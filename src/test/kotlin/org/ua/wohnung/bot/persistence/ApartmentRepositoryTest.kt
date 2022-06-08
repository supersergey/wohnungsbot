package org.ua.wohnung.bot.persistence

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.koin.java.KoinJavaComponent
import org.ua.wohnung.bot.persistence.config.JooqExtension
import org.ua.wohnung.bot.persistence.generated.tables.pojos.Apartment
import org.ua.wohnung.bot.sheets.PublicationStatus
import kotlin.random.Random.Default.nextLong

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

    private fun anApartment(): Apartment {
        return Apartment(
            nextLong(),
            "city",
            "Berlin",
            1,
            10,
            "description",
            false,
            PublicationStatus.ACTIVE.name
        )
    }
}
