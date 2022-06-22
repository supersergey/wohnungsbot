package org.ua.wohnung.bot.persistence

import org.jooq.DSLContext
import org.ua.wohnung.bot.persistence.generated.Tables.ACCOUNT
import org.ua.wohnung.bot.persistence.generated.Tables.APARTMENT_ACCOUNT
import org.ua.wohnung.bot.persistence.generated.Tables.USER_DETAILS
import org.ua.wohnung.bot.persistence.generated.tables.pojos.Account
import org.ua.wohnung.bot.persistence.generated.tables.pojos.UserDetails
import org.ua.wohnung.bot.persistence.generated.tables.records.ApartmentAccountRecord
import java.time.OffsetDateTime

class ApartmentAccountRepository(private val jooq: DSLContext) {
    fun findLatestApplyTs(userId: Int, limit: Int = 2): List<OffsetDateTime> {
        return jooq.select()
            .from(APARTMENT_ACCOUNT)
            .where(APARTMENT_ACCOUNT.ACCOUNT_ID.eq(userId))
            .orderBy(APARTMENT_ACCOUNT.APPLY_TS.desc())
            .limit(limit).fetch()
            .map {
                it as ApartmentAccountRecord
                it.applyTs
            }
    }

    fun save(apartmentId: String, userId: Int) {
        jooq.newRecord(APARTMENT_ACCOUNT).apply {
            this.apartmentId = apartmentId
            this.accountId = userId
            this.applyTs = OffsetDateTime.now()
        }.store()
    }

    fun findAccountsByApartmentId(apartmentId: String): List<ApartmentApplication> {
        return jooq
            .select().from(APARTMENT_ACCOUNT)
            .join(ACCOUNT).on(APARTMENT_ACCOUNT.ACCOUNT_ID.eq(ACCOUNT.ID))
            .join(USER_DETAILS).on(APARTMENT_ACCOUNT.ACCOUNT_ID.eq(USER_DETAILS.ID))
            .where(APARTMENT_ACCOUNT.APARTMENT_ID.eq(apartmentId))
            .map {
                ApartmentApplication(
                    apartmentId = it[APARTMENT_ACCOUNT.APARTMENT_ID],
                    account = Account(
                        it[ACCOUNT.ID],
                        it[ACCOUNT.CHAT_ID],
                        it[ACCOUNT.USERNAME],
                        it[ACCOUNT.ROLE]
                    ),
                    userDetails = UserDetails(
                        it[USER_DETAILS.ID],
                        it[USER_DETAILS.FIRST_LAST_NAME],
                        it[USER_DETAILS.PHONE],
                        it[USER_DETAILS.NUMBER_OF_TENANTS],
                        it[USER_DETAILS.PETS],
                        it[USER_DETAILS.BUNDESLAND]
                    )
                )
            }
    }

    fun deleteByUserId(userId: Int, dslContext: DSLContext = jooq) {
        dslContext.deleteFrom(APARTMENT_ACCOUNT).where(APARTMENT_ACCOUNT.ACCOUNT_ID.eq(userId)).execute()
    }
}

data class ApartmentApplication(
    val apartmentId: String,
    val account: Account,
    val userDetails: UserDetails
)
