package org.ua.wohnung.bot.persistence

import org.jooq.DSLContext
import org.ua.wohnung.bot.persistence.generated.enums.Role
import org.ua.wohnung.bot.persistence.generated.tables.Account.ACCOUNT
import org.ua.wohnung.bot.persistence.generated.tables.pojos.Account

class AccountRepository(private val dslContext: DSLContext) {

    fun save(account: Account) {
        val accountRecord =
            dslContext.fetchOne(ACCOUNT, ACCOUNT.USERNAME.eq(account.username)) ?: dslContext.newRecord(ACCOUNT)
        accountRecord.apply {
            username = account.username
            role = Role.valueOf(account.role.toString())
        }.store()
    }

    fun findById(username: String): Account? {
        return dslContext.fetchOne(ACCOUNT, ACCOUNT.USERNAME.eq(username))?.let {
            Account(it.username, it.role)
        }
    }

    fun deleteById(login: String) {
        dslContext.fetchOne(ACCOUNT, ACCOUNT.USERNAME.eq(login))?.delete()
    }
}
