package org.ua.wohnung.bot.persistence

import org.assertj.core.api.Assertions.assertThat
import org.jooq.DSLContext
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.koin.java.KoinJavaComponent
import org.ua.wohnung.bot.persistence.config.JooqExtension
import org.ua.wohnung.bot.sheets.PublicationStatus
import org.ua.wohnung.bot.user.model.BundesLand
import org.ua.wohnung.bot.util.anApartment

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

    @Test
    fun `should search for aparmtents`() {
        val expected = anApartment(bundesLand = BundesLand.BERLIN, minTenants = 1, maxTenants = 5, petsAllowed = true, publicationStatus = PublicationStatus.ACTIVE)
        val apartments = listOf(
            anApartment(),
            expected,
            anApartment()
        )
        apartmentRepository.saveAll(dslContext, apartments)

        val actual = apartmentRepository.findByCriteria(
            ApartmentSearchCriteria(bundesLand = BundesLand.BERLIN, numberOfTenants = 3, petsAllowed = true, publicationStatus = PublicationStatus.ACTIVE)
        )

        assertThat(actual).singleElement().usingRecursiveComparison().isEqualTo(expected)
    }
}
