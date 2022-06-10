package org.ua.wohnung.bot.persistence

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.koin.java.KoinJavaComponent
import org.ua.wohnung.bot.persistence.config.JooqExtension
import org.ua.wohnung.bot.util.anApartment

@ExtendWith(JooqExtension::class)
internal class ApartmentRepositoryTest {

    private val apartmentRepository: ApartmentRepository by KoinJavaComponent.inject(ApartmentRepository::class.java)

    @Test
    fun `should save the apartment record`() {
        val anApartment = anApartment()
        apartmentRepository.save(anApartment)

        val saved = apartmentRepository.findById(anApartment.id)

        assertThat(saved).usingRecursiveComparison().isEqualTo(anApartment)
    }

    @Test
    fun `should save batch of apartment records`() {
        val apartments = listOf(anApartment(), anApartment())
        apartmentRepository.saveAll(apartments)
        apartmentRepository.saveAll(apartments + anApartment())
        assertThat(apartmentRepository.count()).isEqualTo(3)
    }
}
