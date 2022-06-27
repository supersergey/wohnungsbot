package org.ua.wohnung.bot.user

import org.jooq.DSLContext
import org.ua.wohnung.bot.exception.ServiceException.UserNotFound
import org.ua.wohnung.bot.persistence.AccountRepository
import org.ua.wohnung.bot.persistence.ApartmentAccountRepository
import org.ua.wohnung.bot.persistence.UserDetailsRepository
import org.ua.wohnung.bot.persistence.UserInfo
import org.ua.wohnung.bot.persistence.generated.enums.Role
import org.ua.wohnung.bot.persistence.generated.tables.pojos.Account
import org.ua.wohnung.bot.persistence.generated.tables.pojos.UserDetails

class UserService(
    private val accountRepository: AccountRepository,
    private val userDetailsRepository: UserDetailsRepository,
    private val apartmentAccountRepository: ApartmentAccountRepository,
    private val dslContext: DSLContext
) {
    fun findById(userId: Long): UserDetails? {
        return userDetailsRepository.findById(userId)
    }

    fun findUserRoleById(userId: Long): Role? {
        return accountRepository.findById(userId)?.role
    }

    fun findByRole(role: Role): List<UserInfo> {
        return userDetailsRepository.findByRole(role)
    }

    fun createAccount(account: Account) {
        dslContext.transaction { _ ->
            accountRepository.save(account)
        }
    }

    fun updateUserDetails(userDetails: UserDetails) {
        dslContext.transaction { _ ->
            accountRepository.findById(userDetails.id)
                ?: throw UserNotFound(userDetails.id)
            userDetailsRepository.save(userDetails)
        }
    }

    fun delete(userId: Long) {
        dslContext.transaction { ctx ->
            apartmentAccountRepository.deleteByUserId(userId, ctx.dsl())
            userDetailsRepository.deleteById(userId, ctx.dsl())
            accountRepository.deleteById(userId, ctx.dsl())
        }
    }
}
