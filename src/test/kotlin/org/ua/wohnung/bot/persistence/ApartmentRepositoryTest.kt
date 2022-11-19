package org.ua.wohnung.bot.persistence

import org.assertj.core.api.Assertions.assertThat
import org.jooq.DSLContext
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import org.koin.java.KoinJavaComponent
import org.ua.wohnung.bot.persistence.config.JooqExtension
import org.ua.wohnung.bot.persistence.generated.tables.pojos.Apartment
import org.ua.wohnung.bot.sheets.PublicationStatus
import org.ua.wohnung.bot.user.model.BundesLand.BERLIN
import org.ua.wohnung.bot.util.anApartment
import java.util.stream.Stream

@ExtendWith(JooqExtension::class)
internal class ApartmentRepositoryTest {

    private val apartmentRepository: ApartmentRepository by KoinJavaComponent.inject(ApartmentRepository::class.java)
    private val dslContext: DSLContext by KoinJavaComponent.inject(DSLContext::class.java)

    @Test
    fun `should save and update the apartment record`() {
        val anApartment = anApartment()
        apartmentRepository.save(anApartment)
        apartmentRepository.save(anApartment.apply { city = "anotherCity" })

        val saved = apartmentRepository.findById(anApartment.id)

        assertThat(saved).usingRecursiveComparison().isEqualTo(anApartment)
        assertThat(apartmentRepository.count()).isEqualTo(1)
    }

    @Test
    fun `should save batch of apartment records`() {
        val apartments = listOf(anApartment(), anApartment())
        apartmentRepository.saveAll(dslContext, apartments)
        apartmentRepository.saveAll(dslContext, apartments + anApartment())
        assertThat(apartmentRepository.count()).isEqualTo(3)
    }

    @ParameterizedTest
    @MethodSource("apartmentsSource")
    fun `should search for apartments`(expected: Apartment, apartmentSearchCriteria: ApartmentSearchCriteria) {
        val apartments = listOf(
            anApartment(),
            expected,
            anApartment()
        )
        apartmentRepository.saveAll(dslContext, apartments)

        val actual = apartmentRepository.findByCriteria(apartmentSearchCriteria)

        assertThat(actual).singleElement().usingRecursiveComparison().isEqualTo(expected)
    }

    @Test
    fun `when user has wbs, should find only a WBS apartment with the LE number of rooms`() {
        val apartments = listOf(
            anApartment(bundesLand = BERLIN, wbs = true, numberOrRooms = 2),
            anApartment(bundesLand = BERLIN, wbs = true, numberOrRooms = 1),
            anApartment(bundesLand = BERLIN, wbs = false, numberOrRooms = 1),
            anApartment(bundesLand = BERLIN, wbs = false, numberOrRooms = 2),
            anApartment(bundesLand = BERLIN, wbs = true, numberOrRooms = 3)
        )
        apartmentRepository.saveAll(dslContext, apartments)

        val actual = apartmentRepository.findByCriteria(
            ApartmentSearchCriteria(BERLIN, 2, null, PublicationStatus.ACTIVE, true, 2)
        )

        assertThat(actual.onEach { it.id = "1" }.toList())
            .usingRecursiveFieldByFieldElementComparator().containsExactlyInAnyOrder(
                anApartment(id = 1, bundesLand = BERLIN, wbs = true, numberOrRooms = 2),
                anApartment(id = 1, bundesLand = BERLIN, wbs = true, numberOrRooms = 1)
            )
    }

    companion object {
        @JvmStatic
        fun apartmentsSource(): Stream<Arguments> {
            return Stream.of(
                arguments(
                    anApartment(
                        bundesLand = BERLIN,
                        minTenants = 1,
                        maxTenants = 5,
                        petsAllowed = true,
                        publicationStatus = PublicationStatus.ACTIVE,
                        wbs = false
                    ),
                    ApartmentSearchCriteria(
                        bundesLand = BERLIN,
                        numberOfTenants = 3,
                        petsAllowed = true,
                        publicationStatus = PublicationStatus.ACTIVE,
                        wbs = false
                    )
                ),
                arguments(
                    anApartment(
                        bundesLand = BERLIN,
                        minTenants = 1,
                        maxTenants = 5,
                        petsAllowed = true,
                        publicationStatus = PublicationStatus.ACTIVE,
                        wbs = true
                    ),
                    ApartmentSearchCriteria(
                        bundesLand = BERLIN,
                        numberOfTenants = 3,
                        petsAllowed = true,
                        publicationStatus = PublicationStatus.ACTIVE,
                        wbs = true
                    )
                )
            )
        }
    }
}
