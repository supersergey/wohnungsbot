package org.ua.wohnung.bot.persistence

import org.jooq.DSLContext
import org.ua.wohnung.bot.persistence.generated.Tables.ACCOUNT
import org.ua.wohnung.bot.persistence.generated.Tables.USER_DETAILS
import org.ua.wohnung.bot.persistence.generated.enums.Role
import org.ua.wohnung.bot.persistence.generated.tables.pojos.UserDetails
import org.ua.wohnung.bot.persistence.generated.tables.records.UserDetailsRecord

class UserDetailsRepository(private val jooq: DSLContext) {
    fun save(userDetails: UserDetails) {
        val userDetailsRecord = jooq
            .fetchOne(USER_DETAILS, USER_DETAILS.ID.eq(userDetails.id))
            ?: jooq.newRecord(USER_DETAILS)

        userDetailsRecord.apply {
            id = userDetails.id
            firstLastName = userDetails.firstLastName
            phone = userDetails.phone
            numberOfTenants = userDetails.numberOfTenants
            pets = userDetails.pets
            bundesland = userDetails.bundesland
            familyMembers = userDetails.familyMembers
            readyToMove = userDetails.readyToMove
            foreignLanguages = userDetails.foreignLanguages
            district = userDetails.district
            allergies = userDetails.allergies
            email = userDetails.email
            wbs = userDetails.wbs
            wbsDetails = userDetails.wbsDetails
        }
        userDetailsRecord.store()
    }

    fun findById(id: Long): UserDetails? =
        jooq.fetchOne(USER_DETAILS, USER_DETAILS.ID.eq(id))?.map {
            it as UserDetailsRecord
            it.toUserDetails()
        }

    fun deleteById(id: Long, dslContext: DSLContext = jooq) {
        dslContext.deleteFrom(USER_DETAILS).where(USER_DETAILS.ID.eq(id)).execute()
    }

    fun findByRole(role: Role): List<UserInfo> {
        return jooq.select(
            USER_DETAILS.ID,
            ACCOUNT.CHAT_ID,
            ACCOUNT.USERNAME,
            USER_DETAILS.FIRST_LAST_NAME,
            ACCOUNT.ROLE
        )
            .from(USER_DETAILS)
            .join(ACCOUNT)
            .on(USER_DETAILS.ID.eq(ACCOUNT.ID))
            .where(ACCOUNT.ROLE.eq(role))
            .map {
                UserInfo(it.value1(), it.value2(), it.value3() ?: "невідомо", it.value4(), it.value5())
            }
    }

    private fun UserDetailsRecord.toUserDetails(): UserDetails =
        UserDetails(
            id,
            firstLastName,
            phone,
            numberOfTenants,
            pets,
            bundesland,
            district,
            familyMembers,
            readyToMove,
            foreignLanguages,
            allergies,
            email,
            wbs,
            wbsDetails
        )
}

data class UserInfo(
    val userId: Long,
    val chatId: Long,
    val userName: String,
    val firstAndLastName: String,
    val role: Role
)
