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
            id = account.id
            chatId = account.chatId
            username = account.username
            role = Role.valueOf(account.role.toString())
        }.store()
    }

    fun findById(userId: Long): Account? {
        return dslContext.fetchOne(ACCOUNT, ACCOUNT.ID.eq(userId))?.let {
            Account(it.id, it.chatId, it.username, it.role)
        }
    }

    fun deleteById(userId: Long) {
        dslContext.fetchOne(ACCOUNT, ACCOUNT.ID.eq(userId))?.delete()
    }
}
