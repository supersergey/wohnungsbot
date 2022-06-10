package org.ua.wohnung.bot.persistence

import org.jooq.DSLContext
import org.ua.wohnung.bot.persistence.generated.Tables.APARTMENT
import org.ua.wohnung.bot.persistence.generated.tables.pojos.Apartment
import org.ua.wohnung.bot.persistence.generated.tables.records.ApartmentRecord

class ApartmentRepository(private val dslContext: DSLContext) {
    fun findById(apartmentId: Long): Apartment? =
        dslContext.fetchOne(APARTMENT, APARTMENT.ID.eq(apartmentId))?.map {
            it as ApartmentRecord
            Apartment(
                it.id,
                it.city,
                it.bundesland,
                it.minTenants,
                it.maxTenants,
                it.description,
                it.petsAllowed,
                it.publicationstatus
            )
        }

    fun save(apartment: Apartment) {
        val apartmentRecord = dslContext.fetchOne(APARTMENT, APARTMENT.ID.eq(apartment.id))
            ?: dslContext.newRecord(APARTMENT)
        apartmentRecord.apply {
            id = apartment.id
            city = apartment.city
            bundesland = apartment.bundesland
            minTenants = apartment.minTenants
            maxTenants = apartment.maxTenants
            description = apartment.description
            petsAllowed = apartment.petsAllowed
            publicationstatus = apartment.publicationstatus
        }.store()
    }

    fun saveAll(apartments: Collection<Apartment>): Int {
        return dslContext.batchMerge(
            apartments.map {
                ApartmentRecord(
                    it.id,
                    it.city,
                    it.bundesland,
                    it.minTenants,
                    it.maxTenants,
                    it.description,
                    it.petsAllowed,
                    it.publicationstatus
                )
            }
        ).execute().size
    }

    fun count(): Int {
        return dslContext.fetchCount(APARTMENT)
    }
}
