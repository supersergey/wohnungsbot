package org.ua.wohnung.bot.persistence

import org.jooq.DSLContext
import org.ua.wohnung.bot.dto.AccountDto
import org.ua.wohnung.bot.persistence.generated.enums.Role
import org.ua.wohnung.bot.persistence.generated.tables.Account.ACCOUNT
import org.ua.wohnung.bot.persistence.generated.tables.records.AccountRecord

class AccountRepository(private val dslContext: DSLContext) {

    fun save(accountDto: AccountDto) {
        dslContext.transaction { _ ->
            val account = dslContext.fetchOne(ACCOUNT, ACCOUNT.LOGIN.eq(accountDto.login)) ?: dslContext.newRecord(ACCOUNT)
            account.apply {
                this.login = accountDto.login
                this.role = Role.valueOf(accountDto.role.toString())
            }.store()
        }
    }

    fun findByLogin(login: String): AccountDto? {
        return dslContext.fetchOne(ACCOUNT, ACCOUNT.LOGIN.eq(login))?.toDto()
    }

    fun deleteByLogin(login: String) {
        dslContext.fetchOne(ACCOUNT, ACCOUNT.LOGIN.eq(login))?.delete()
    }

    private fun AccountRecord.toDto(): AccountDto {
        return AccountDto(
            login = this.login,
            role = org.ua.wohnung.bot.dto.Role.valueOf(this.role.toString())
        )
    }
}
