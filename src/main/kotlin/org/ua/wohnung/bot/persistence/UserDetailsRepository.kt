package org.ua.wohnung.bot.persistence

import org.jooq.DSLContext
import org.ua.wohnung.bot.persistence.generated.Tables.USER_DETAILS
import org.ua.wohnung.bot.persistence.generated.tables.pojos.UserDetails

class UserDetailsRepository(private val dslContext: DSLContext) {
    fun save(userDetails: UserDetails) {
        val userDetailsRecord = dslContext
            .fetchOne(USER_DETAILS, USER_DETAILS.USERNAME.eq(userDetails.username))
            ?: dslContext.newRecord(USER_DETAILS)

        userDetailsRecord.apply {
            username = userDetails.username
            firstLastName = userDetails.firstLastName
            phone = userDetails.phone
            numberOfTenants = userDetails.numberOfTenants
            pets = userDetails.pets
            bundesland = userDetails.bundesland
        }
        userDetailsRecord.store()
    }

    fun findById(username: String): UserDetails? =
        dslContext.fetchOne(USER_DETAILS, USER_DETAILS.USERNAME.eq(username))?.let {
            UserDetails(
                it.username,
                it.firstLastName,
                it.phone,
                it.numberOfTenants,
                it.pets,
                it.bundesland
            )
        }

    fun deleteById(username: String) {
        dslContext.deleteFrom(USER_DETAILS).where(USER_DETAILS.USERNAME.eq(username)).execute()
    }
}
