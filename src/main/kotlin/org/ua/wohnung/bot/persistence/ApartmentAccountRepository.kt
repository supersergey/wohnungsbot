package org.ua.wohnung.bot.persistence

import org.jooq.DSLContext
import org.ua.wohnung.bot.persistence.generated.Tables.APARTMENT_ACCOUNT
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
}
