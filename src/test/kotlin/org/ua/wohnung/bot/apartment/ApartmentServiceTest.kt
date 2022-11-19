package org.ua.wohnung.bot.apartment

import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.jooq.Configuration
import org.jooq.DSLContext
import org.jooq.TransactionalRunnable
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import org.ua.wohnung.bot.configuration.MessageSource
import org.ua.wohnung.bot.persistence.AccountRepository
import org.ua.wohnung.bot.persistence.ApartmentAccountRepository
import org.ua.wohnung.bot.persistence.ApartmentApplication
import org.ua.wohnung.bot.persistence.ApartmentRepository
import org.ua.wohnung.bot.persistence.ApartmentSearchCriteria
import org.ua.wohnung.bot.persistence.UserDetailsRepository
import org.ua.wohnung.bot.persistence.generated.enums.Role
import org.ua.wohnung.bot.persistence.generated.tables.pojos.Account
import org.ua.wohnung.bot.persistence.generated.tables.pojos.Apartment
import org.ua.wohnung.bot.persistence.generated.tables.pojos.UserDetails
import org.ua.wohnung.bot.sheets.PublicationStatus
import org.ua.wohnung.bot.sheets.RowMapper
import org.ua.wohnung.bot.sheets.SheetReader
import org.ua.wohnung.bot.user.model.BundesLand
import java.time.Duration
import java.time.OffsetDateTime
import java.util.UUID
import java.util.stream.Stream

@ExtendWith(MockKExtension::class)
internal class ApartmentServiceTest {

    @MockK
    private lateinit var dslContext: DSLContext

    @MockK
    private lateinit var accountRepository: AccountRepository

    @MockK
    private lateinit var userDetailsRepository: UserDetailsRepository

    @MockK
    private lateinit var apartmentRepository: ApartmentRepository

    @MockK
    private lateinit var apartmentAccountRepository: ApartmentAccountRepository

    @RelaxedMockK
    private lateinit var sheetReader: SheetReader

    @MockK
    private lateinit var rowMapper: RowMapper

    @RelaxedMockK
    private lateinit var messageSource: MessageSource

    @InjectMockKs
    private lateinit var apartmentService: ApartmentService

    @BeforeEach
    internal fun setUp() {
        clearAllMocks()
    }

    @Test
    fun `should mark apartments that are not present in the source as INACTIVE`() {
        val activeApartment = anApartment("activeApartment", PublicationStatus.ACTIVE)
        val activeApartmentMissingInIncomingList = anApartment("activeApartmentMissingInIncomingList", PublicationStatus.ACTIVE)
        val newApartment = anApartment("newApartment", PublicationStatus.ACTIVE)
        val apartmentTheBecomesInactive = anApartment("apartmentTheBecomesInactive", PublicationStatus.NOT_ACTIVE)
        val apartmentThatRemainsNotActive = anApartment("apartmentThatRemainsNotActive", PublicationStatus.NOT_ACTIVE)

        every { sheetReader.readRows() } returns listOf(mockk(), mockk(), mockk(), mockk())
        every { rowMapper.invoke(any()) } returns
            activeApartment andThen
            newApartment andThen
            apartmentTheBecomesInactive andThen
            apartmentThatRemainsNotActive

        val dslConfig = mockk<Configuration>()
        val internalDslContext = mockk<DSLContext>()
        every { dslConfig.dsl() } returns internalDslContext
        every { dslContext.transaction(any<TransactionalRunnable>()) } answers {
            lastArg<TransactionalRunnable>().run(
                dslConfig
            )
        }
        every { apartmentRepository.findByCriteria(any()) } returns listOf(
            activeApartment,
            activeApartmentMissingInIncomingList,
            apartmentTheBecomesInactive,
            apartmentThatRemainsNotActive
        )
        every { apartmentRepository.count() } returns 1
        every { apartmentRepository.saveAll(any(), any()) } returns 1

        apartmentService.update()

        verify {
            apartmentRepository.saveAll(
                internalDslContext,
                listOf(
                    activeApartment,
                    newApartment,
                    apartmentTheBecomesInactive,
                    apartmentThatRemainsNotActive
                )
            )
            apartmentRepository.saveAll(
                internalDslContext,
                listOf(
                    activeApartmentMissingInIncomingList.apply {
                        publicationstatus = PublicationStatus.NOT_ACTIVE.name
                    }
                )
            )
        }
    }

    @Test
    fun `should rate-limit user applications`() {
        val account = mockk<Account>(relaxed = true).also {
            every { it.role } returns Role.USER
            every { it.id } returns 1L
        }
        every { accountRepository.findById(any()) } returns account
        every { apartmentRepository.findById(any()) } returns anApartment()
        every { apartmentAccountRepository.findLatestApplyTs(any(), any()) } returns listOf(
            OffsetDateTime.now(), OffsetDateTime.now().minus(Duration.ofDays(1))
        )
        val apartmentApplication = mockk<ApartmentApplication>().also {
            every { it.account.id } returns 1
        }
        every { apartmentAccountRepository.findAccountsByApartmentId(any(), any(), any()) } returns listOf(apartmentApplication)
        every { apartmentAccountRepository.save(any(), any()) } just Runs

        apartmentService.acceptUserApartmentRequest(123L, "1")

        verify {
            apartmentAccountRepository.save("1", 123L)
        }
    }

    @ParameterizedTest
    @MethodSource("wbsSearchCriteria")
    fun `should build a search criteria with WBS`(
        userWbs: Boolean,
        userNumberOfRooms: Int?,
        criteriaWbs: Boolean?,
        criteriaNumberOfRooms: Int?
    ) {
        val userId = 1L

        val userDetails = mockk<UserDetails>(relaxed = true).also {
            every { it.id } returns 1L
            every { it.wbs } returns userWbs
            every { it.wbsNumberOfRooms } returns userNumberOfRooms?.toShort()
            every { it.bundesland } returns BundesLand.BRANDENBURG.germanName
            every { it.numberOfTenants } returns 2
            every { it.pets } returns true
        }
        every { userDetailsRepository.findById(any()) } returns userDetails
        val apartmentSearchCriteria = slot<ApartmentSearchCriteria>()
        every { apartmentRepository.findByCriteria(capture(apartmentSearchCriteria)) } returns emptyList()
        every { sheetReader.readRows() } returns emptyList()

        apartmentService.findByUserDetails(userId)

        assertThat(apartmentSearchCriteria.captured).isEqualTo(
            ApartmentSearchCriteria(
                bundesLand = BundesLand.BRANDENBURG,
                numberOfTenants = 2,
                petsAllowed = true,
                publicationStatus = PublicationStatus.ACTIVE,
                wbs = criteriaWbs,
                numberOfRooms = criteriaNumberOfRooms?.toShort()
            )
        )
    }

    private fun anApartment(
        id: String = UUID.randomUUID().toString(),
        publicationStatus: PublicationStatus = PublicationStatus.ACTIVE
    ): Apartment =
        Apartment(
            id,
            "Berlin",
            "Berlin",
            2,
            3,
            "",
            true,
            publicationStatus.name,
            "",
            "",
            "",
            true,
            "wbs",
            2
        )

    companion object {
        @JvmStatic
        fun wbsSearchCriteria(): Stream<Arguments> {
            return Stream.of(
                arguments(true, 2, null, 2),
                arguments(false, null, false, null)
            )
        }
    }
}
