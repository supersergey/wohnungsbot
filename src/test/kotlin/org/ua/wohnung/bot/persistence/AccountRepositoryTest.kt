package org.ua.wohnung.bot.persistence

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.koin.java.KoinJavaComponent.inject
import org.ua.wohnung.bot.persistence.config.JooqExtension
import org.ua.wohnung.bot.persistence.generated.enums.Role.ADMIN
import org.ua.wohnung.bot.persistence.generated.enums.Role.USER
import org.ua.wohnung.bot.persistence.generated.tables.pojos.Account

@ExtendWith(JooqExtension::class)
internal class AccountRepositoryTest {
    private val accountRepository: AccountRepository by inject(AccountRepository::class.java)

    @Test
    fun `should save the userRecord`() {
        val accountRecord = Account("my_login", ADMIN)
        accountRepository.save(accountRecord)
        val saved = accountRepository.findById(accountRecord.username)
        assertThat(saved).usingRecursiveComparison().isEqualTo(accountRecord)
    }

    @Test
    fun `should update the userRecord`() {
        val accountRecord = Account("my_login", USER)
        accountRepository.save(accountRecord)
        val newAccountRecord = Account("my_login", ADMIN)
        accountRepository.save(newAccountRecord)
        val saved = accountRepository.findById(accountRecord.username)
        assertThat(saved).usingRecursiveComparison().isEqualTo(newAccountRecord)
    }
}
