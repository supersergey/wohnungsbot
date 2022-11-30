package org.ua.wohnung.bot.persistence

import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.SelectSeekStep1
import org.ua.wohnung.bot.persistence.generated.Tables.ACCOUNT
import org.ua.wohnung.bot.persistence.generated.Tables.APARTMENT_ACCOUNT
import org.ua.wohnung.bot.persistence.generated.Tables.USER_DETAILS
import org.ua.wohnung.bot.persistence.generated.tables.pojos.Account
import org.ua.wohnung.bot.persistence.generated.tables.pojos.UserDetails
import org.ua.wohnung.bot.persistence.generated.tables.records.ApartmentAccountRecord
import java.time.OffsetDateTime

class ApartmentAccountRepository(private val jooq: DSLContext) {
    fun findLatestApplyTs(userId: Long, limit: Int = 2): List<OffsetDateTime> {
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

    fun save(apartmentId: String, userId: Long) {
        jooq.newRecord(APARTMENT_ACCOUNT).apply {
            this.apartmentId = apartmentId
            this.accountId = userId
            this.applyTs = OffsetDateTime.now()
        }.store()
    }

    fun countAccountsByApartmentId(apartmentId: String): Int =
        jooq.prepareFindAccountsByApartmentId(apartmentId).count()

    fun isUserAlreadyAppliedForApartment(apartmentId: String, userId: Long): Boolean =
        jooq.fetchCount(
            APARTMENT_ACCOUNT,
            APARTMENT_ACCOUNT.ACCOUNT_ID.eq(userId),
            APARTMENT_ACCOUNT.APARTMENT_ID.eq(apartmentId)
        ) > 0

    fun findAccountsByApartmentId(apartmentId: String, offset: Int, limit: Int): List<ApartmentApplication> {
        return jooq.prepareFindAccountsByApartmentId(apartmentId)
            .offset(offset)
            .limit(limit)
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
                        it[USER_DETAILS.BUNDESLAND],
                        it[USER_DETAILS.DISTRICT],
                        it[USER_DETAILS.FAMILY_MEMBERS],
                        it[USER_DETAILS.READY_TO_MOVE],
                        it[USER_DETAILS.FOREIGN_LANGUAGES],
                        it[USER_DETAILS.ALLERGIES],
                        it[USER_DETAILS.EMAIL],
                        it[USER_DETAILS.WBS],
                        it[USER_DETAILS.WBS_DETAILS],
                        it[USER_DETAILS.WBS_NUMBER_OF_ROOMS]
                    )
                )
            }
    }

    private fun DSLContext.prepareFindAccountsByApartmentId(apartmentId: String): SelectSeekStep1<Record, OffsetDateTime> {
        return select().from(APARTMENT_ACCOUNT)
            .join(ACCOUNT).on(APARTMENT_ACCOUNT.ACCOUNT_ID.eq(ACCOUNT.ID))
            .join(USER_DETAILS).on(APARTMENT_ACCOUNT.ACCOUNT_ID.eq(USER_DETAILS.ID))
            .where(APARTMENT_ACCOUNT.APARTMENT_ID.eq(apartmentId))
            .and(APARTMENT_ACCOUNT.HIDDEN.eq(false))
            .orderBy(APARTMENT_ACCOUNT.APPLY_TS.asc())
    }

    fun deleteByUserId(userId: Long, dslContext: DSLContext = jooq) {
        dslContext.deleteFrom(APARTMENT_ACCOUNT).where(APARTMENT_ACCOUNT.ACCOUNT_ID.eq(userId)).execute()
    }

    fun hideApplication(apartmentId: String, userId: Long) {
        jooq.update(APARTMENT_ACCOUNT)
            .set(APARTMENT_ACCOUNT.HIDDEN, true)
            .where(APARTMENT_ACCOUNT.ACCOUNT_ID.eq(userId))
            .and(APARTMENT_ACCOUNT.APARTMENT_ID.eq(apartmentId))
            .execute()
    }
}

data class ApartmentApplication(
    val apartmentId: String,
    val account: Account,
    val userDetails: UserDetails
)
