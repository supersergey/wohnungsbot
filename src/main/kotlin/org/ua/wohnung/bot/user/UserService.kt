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

    fun isRegistrationComplete(userId: Long): Boolean {
        val account = accountRepository.findById(userId)
        val userDetails = userDetailsRepository.findById(userId)
        return account?.role == Role.USER && userDetails.isComplete()

    }

    private fun UserDetails?.isComplete(): Boolean =
        listOfNotNull(
            this?.id,
            this?.bundesland,
            this?.phone,
            this?.pets,
            this?.firstLastName,
            this?.numberOfTenants,
            this?.district,
            this?.familyMembers,
            this?.allergies,
            this?.foreignLanguages,
            this?.readyToMove
        ).size == 11

    fun createAccount(account: Account) {
        dslContext.transaction { _ ->
            accountRepository.save(account)
        }
    }

    fun updateUserDetails(id: Long, block: UserDetails.() -> Unit = {}) {
        dslContext.transaction { _ ->
            accountRepository.findById(id)
                ?: throw UserNotFound(id)
            val userDetails = userDetailsRepository.findById(id) ?: UserDetails().apply { this.id = id }
            userDetails.apply(block)
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
