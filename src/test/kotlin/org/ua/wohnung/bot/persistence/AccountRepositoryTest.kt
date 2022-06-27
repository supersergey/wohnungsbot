package org.ua.wohnung.bot.persistence

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.koin.java.KoinJavaComponent.inject
import org.ua.wohnung.bot.persistence.config.JooqExtension
import org.ua.wohnung.bot.persistence.generated.enums.Role.ADMIN
import org.ua.wohnung.bot.persistence.generated.enums.Role.USER
import org.ua.wohnung.bot.persistence.generated.tables.pojos.Account
import kotlin.random.Random.Default.nextLong

@ExtendWith(JooqExtension::class)
internal class AccountRepositoryTest {
    private val accountRepository: AccountRepository by inject(AccountRepository::class.java)

    @Test
    fun `should save the userRecord`() {
        val accountRecord = Account(nextLong(), nextLong(), "username", ADMIN)
        accountRepository.save(accountRecord)
        val saved = accountRepository.findById(accountRecord.id)
        assertThat(saved).usingRecursiveComparison().isEqualTo(accountRecord)
    }

    @Test
    fun `should update the userRecord`() {
        val id = nextLong()
        val accountRecord = Account(id, nextLong(), "username", ADMIN)
        accountRepository.save(accountRecord)
        val newAccountRecord = Account(id, nextLong(), "username", USER)
        accountRepository.save(newAccountRecord)
        val saved = accountRepository.findById(accountRecord.id)
        assertThat(saved).usingRecursiveComparison().isEqualTo(newAccountRecord)
    }
}
