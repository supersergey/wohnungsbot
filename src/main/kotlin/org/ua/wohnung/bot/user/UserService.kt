package org.ua.wohnung.bot.user

import org.jooq.DSLContext
import org.ua.wohnung.bot.exception.ServiceException.UserNotFoundException
import org.ua.wohnung.bot.persistence.AccountRepository
import org.ua.wohnung.bot.persistence.UserDetailsRepository
import org.ua.wohnung.bot.persistence.generated.enums.Role
import org.ua.wohnung.bot.persistence.generated.tables.pojos.Account
import org.ua.wohnung.bot.persistence.generated.tables.pojos.UserDetails

class UserService(
    private val accountRepository: AccountRepository,
    private val userDetailsRepository: UserDetailsRepository,
    private val dslContext: DSLContext
) {
    fun findById(username: String): UserDetails? {
        return userDetailsRepository.findById(username)
    }

    fun createAccount(account: Account) {
        dslContext.transaction { _ ->
            accountRepository.save(
                Account(account.username, Role.valueOf(account.role.name))
            )
        }
    }

    fun updateUserDetails(userDetails: UserDetails) {
        dslContext.transaction { _ ->
            accountRepository.findById(userDetails.username)
                ?: throw UserNotFoundException(userDetails.username)
            userDetailsRepository.save(userDetails)
        }
    }

    fun delete(username: String) {
        dslContext.transaction { _ ->
            userDetailsRepository.deleteById(username)
            accountRepository.deleteById(username)
        }
    }
}
