package org.ua.wohnung.bot.persistence

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.koin.java.KoinJavaComponent.inject
import org.ua.wohnung.bot.dto.AccountDto
import org.ua.wohnung.bot.dto.Role
import org.ua.wohnung.bot.persistence.config.JooqExtension

@ExtendWith(JooqExtension::class)
internal class AccountRepositoryTest {
    private val accountRepository: AccountRepository by inject(AccountRepository::class.java)

    @Test
    fun `should save the userRecord`() {
        val accountDto = AccountDto("my_login", Role.USER)
        accountRepository.save(accountDto)
        val saved = accountRepository.findByLogin(accountDto.login)
        assertThat(saved).isEqualTo(accountDto)
    }

    @Test
    fun `should update the userRecord`() {
        val accountDto = AccountDto("my_login", Role.USER)
        accountRepository.save(accountDto)
        accountRepository.save(accountDto.copy(role = Role.ADMIN))
        val saved = accountRepository.findByLogin(accountDto.login)
        assertThat(saved).isEqualTo(accountDto.copy(role = Role.ADMIN))
    }
}