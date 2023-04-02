package org.ua.wohnung.bot.persistence

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.koin.java.KoinJavaComponent
import org.ua.wohnung.bot.persistence.config.JooqExtension
import org.ua.wohnung.bot.persistence.generated.tables.pojos.PostCode

@ExtendWith(JooqExtension::class)
class PostCodeRepositoryTest {
    private val postCodeRepository: PostCodeRepository by KoinJavaComponent.inject(PostCodeRepository::class.java)

    @Test
    fun shouldReturnAreaByPostCodeIgnoringSpaces() {
        val actual = postCodeRepository.findByPostCode("26489")

        assertThat(actual).usingRecursiveComparison().isEqualTo(
            PostCode(
                "26489", "Ochtersum", "3462", "3", "Niedersachsen", "Landkreis Wittmund"
            )
        )
    }
}
