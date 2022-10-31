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
import io.mockk.verify
import org.jooq.Configuration
import org.jooq.DSLContext
import org.jooq.TransactionalRunnable
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.ua.wohnung.bot.configuration.MessageSource
import org.ua.wohnung.bot.persistence.AccountRepository
import org.ua.wohnung.bot.persistence.ApartmentAccountRepository
import org.ua.wohnung.bot.persistence.ApartmentApplication
import org.ua.wohnung.bot.persistence.ApartmentRepository
import org.ua.wohnung.bot.persistence.UserDetailsRepository
import org.ua.wohnung.bot.persistence.generated.enums.Role
import org.ua.wohnung.bot.persistence.generated.tables.pojos.Account
import org.ua.wohnung.bot.persistence.generated.tables.pojos.Apartment
import org.ua.wohnung.bot.sheets.PublicationStatus
import org.ua.wohnung.bot.sheets.RowMapper
import org.ua.wohnung.bot.sheets.SheetReader
import java.time.Duration
import java.time.OffsetDateTime
import java.util.UUID

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

    @MockK
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
            ""
        )
}
