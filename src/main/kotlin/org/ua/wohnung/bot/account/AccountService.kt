package org.ua.wohnung.bot.account

import org.jooq.DSLContext
import org.ua.wohnung.bot.persistence.AccountRepository
import org.ua.wohnung.bot.persistence.generated.enums.Role

class AccountService(
    private val accountRepository: AccountRepository,
    private val jooq: DSLContext
) {
    fun updateUserRole(id: Long, role: Role) {
        jooq.transaction { _ ->
            val user = accountRepository.findById(id) ?: return@transaction
            if (user.role != Role.OWNER) {
                accountRepository.updateUserRole(id, role)
            }
        }
    }
}
