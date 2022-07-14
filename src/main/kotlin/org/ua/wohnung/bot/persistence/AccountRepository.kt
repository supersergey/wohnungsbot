package org.ua.wohnung.bot.persistence

import org.jooq.DSLContext
import org.ua.wohnung.bot.persistence.generated.enums.Role
import org.ua.wohnung.bot.persistence.generated.tables.Account.ACCOUNT
import org.ua.wohnung.bot.persistence.generated.tables.pojos.Account

class AccountRepository(private val jooq: DSLContext) {

    fun save(account: Account) {
        val accountRecord =
            jooq.fetchOne(ACCOUNT, ACCOUNT.ID.eq(account.id)) ?: jooq.newRecord(ACCOUNT)
        accountRecord.apply {
            id = account.id
            chatId = account.chatId
            username = account.username
            role = Role.valueOf(account.role.toString())
        }.store()
    }

    fun findById(userId: Long): Account? {
        return jooq.fetchOne(ACCOUNT, ACCOUNT.ID.eq(userId))?.let {
            Account(it.id, it.chatId, it.username, it.role)
        }
    }

    fun deleteById(userId: Long, dslContext: DSLContext = jooq) {
        dslContext.fetchOne(ACCOUNT, ACCOUNT.ID.eq(userId))?.delete()
    }

    fun updateUserRole(id: Long, role: Role) {
        val accountRecord = jooq.fetchOne(ACCOUNT, ACCOUNT.ID.eq(id)) ?: return
        accountRecord.role = role
        accountRecord.update()
    }
}
