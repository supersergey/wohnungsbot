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
    fun findById(userId: Long): UserDetails? {
        return userDetailsRepository.findById(userId)
    }

    fun findUserRoleById(userId: Long): Role? {
        return accountRepository.findById(userId)?.role
    }

    fun createAccount(account: Account) {
        dslContext.transaction { _ ->
            accountRepository.save(account)
        }
    }

    fun updateUserDetails(userDetails: UserDetails) {
        dslContext.transaction { _ ->
            accountRepository.findById(userDetails.id)
                ?: throw UserNotFoundException(userDetails.id)
            userDetailsRepository.save(userDetails)
        }
    }

    fun delete(userId: Long) {
        dslContext.transaction { _ ->
            userDetailsRepository.deleteById(userId)
            accountRepository.deleteById(userId)
        }
    }
}
