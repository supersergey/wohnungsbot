package org.ua.wohnung.bot.persistence

import org.jooq.DSLContext
import org.ua.wohnung.bot.persistence.generated.Tables.APARTMENT
import org.ua.wohnung.bot.persistence.generated.tables.pojos.Apartment
import org.ua.wohnung.bot.persistence.generated.tables.records.ApartmentRecord
import org.ua.wohnung.bot.sheets.PublicationStatus
import org.ua.wohnung.bot.user.model.BundesLand

class ApartmentRepository(private val dslContext: DSLContext) {
    fun findById(apartmentId: String): Apartment? =
        dslContext.fetchOne(APARTMENT, APARTMENT.ID.eq(apartmentId))?.map {
            it as ApartmentRecord
            it.toApartment()
        }

    fun save(apartment: Apartment) {
        val apartmentRecord = dslContext.fetchOne(APARTMENT, APARTMENT.ID.eq(apartment.id))
            ?: dslContext.newRecord(APARTMENT)
        apartmentRecord.updateWith(apartment).store()
    }

    fun saveAll(ctx: DSLContext, apartments: Collection<Apartment>): Int {
        return ctx.batchMerge(
            apartments.map { it.toRecord() }
        ).execute().size
    }

    fun findByCriteria(criteria: ApartmentSearchCriteria): Collection<Apartment> {
        val criterias = listOfNotNull(
            criteria.bundesLand?.let { APARTMENT.BUNDESLAND.eq(it.germanName) },
            criteria.numberOfTenants?.let { APARTMENT.MIN_TENANTS.le(it.toShort()) },
            criteria.numberOfTenants?.let { APARTMENT.MAX_TENANTS.ge(it.toShort()) },
            criteria.petsAllowed?.let { APARTMENT.PETS_ALLOWED.eq(true) },
            criteria.publicationStatus?.let { APARTMENT.PUBLICATIONSTATUS.eq(it.name) },
            criteria.wbs?.let { APARTMENT.WBS.eq(it) }
        )

        return dslContext.fetch(APARTMENT, criterias).map {
            it.toApartment()
        }
    }

    fun count(): Int {
        return dslContext.fetchCount(APARTMENT, APARTMENT.PUBLICATIONSTATUS.eq(PublicationStatus.ACTIVE.name))
    }

    private fun ApartmentRecord.toApartment(): Apartment =
        Apartment(
            id,
            city,
            bundesland,
            minTenants,
            maxTenants,
            description,
            petsAllowed,
            publicationstatus,
            etage,
            mapLocation,
            livingPeriod,
            showingDate,
            wbs,
            wbsDetails
        )

    private fun ApartmentRecord.updateWith(apartment: Apartment): ApartmentRecord =
        this.apply {
            id = apartment.id
            city = apartment.city
            bundesland = apartment.bundesland
            minTenants = apartment.minTenants
            maxTenants = apartment.maxTenants
            description = apartment.description
            petsAllowed = apartment.petsAllowed
            publicationstatus = apartment.publicationstatus
            etage = apartment.etage
            mapLocation = apartment.mapLocation
            livingPeriod = apartment.livingPeriod
            showingDate = apartment.showingDate
            wbs = apartment.wbs
            wbsDetails = apartment.wbsDetails
        }

    private fun Apartment.toRecord(): ApartmentRecord =
        ApartmentRecord(
            id,
            city,
            bundesland,
            minTenants,
            maxTenants,
            description,
            petsAllowed,
            publicationstatus,
            etage,
            mapLocation,
            livingPeriod,
            showingDate,
            wbs,
            wbsDetails
        )
}

data class ApartmentSearchCriteria(
    var bundesLand: BundesLand? = null,
    var numberOfTenants: Int? = null,
    var petsAllowed: Boolean? = null,
    var publicationStatus: PublicationStatus? = null,
    var wbs: Boolean? = null
)
