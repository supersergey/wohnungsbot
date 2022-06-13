package org.ua.wohnung.bot.persistence

import org.jooq.DSLContext
import org.ua.wohnung.bot.persistence.generated.Tables.USER_DETAILS
import org.ua.wohnung.bot.persistence.generated.tables.pojos.UserDetails

class UserDetailsRepository(private val dslContext: DSLContext) {
    fun save(userDetails: UserDetails) {
        val userDetailsRecord = dslContext
            .fetchOne(USER_DETAILS, USER_DETAILS.ID.eq(userDetails.id))
            ?: dslContext.newRecord(USER_DETAILS)

        userDetailsRecord.apply {
            id = userDetails.id
            firstLastName = userDetails.firstLastName
            phone = userDetails.phone
            numberOfTenants = userDetails.numberOfTenants
            pets = userDetails.pets
            bundesland = userDetails.bundesland
        }
        userDetailsRecord.store()
    }

    fun findById(id: Int): UserDetails? =
        dslContext.fetchOne(USER_DETAILS, USER_DETAILS.ID.eq(id))?.let {
            UserDetails(
                it.id,
                it.firstLastName,
                it.phone,
                it.numberOfTenants,
                it.pets,
                it.bundesland
            )
        }

    fun deleteById(id: Int) {
        dslContext.deleteFrom(USER_DETAILS).where(USER_DETAILS.ID.eq(id)).execute()
    }
}
