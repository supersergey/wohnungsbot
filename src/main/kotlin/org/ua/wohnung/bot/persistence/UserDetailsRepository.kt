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
            this.username = userDetails.username
            this.phone = userDetails.phone
            this.pets = userDetails.pets
            this.bundesLand = userDetails.bundesLand
        }
        userDetailsRecord.store()
    }

    fun findById(username: String): UserDetails? =
        dslContext.fetchOne(USER_DETAILS, USER_DETAILS.USERNAME.eq(username))?.let {
            UserDetails(
                it.username,
                it.phone,
                it.numberOfTenants,
                it.pets,
                it.bundesLand
            )
        }
}
