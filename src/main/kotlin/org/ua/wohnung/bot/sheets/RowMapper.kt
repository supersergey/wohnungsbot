package org.ua.wohnung.bot.sheets

import mu.KotlinLogging
import org.jooq.TableField
import org.ua.wohnung.bot.exception.SheetValidationException
import org.ua.wohnung.bot.persistence.generated.tables.Apartment.APARTMENT
import org.ua.wohnung.bot.persistence.generated.tables.pojos.Apartment
import org.ua.wohnung.bot.persistence.generated.tables.records.ApartmentRecord
import org.ua.wohnung.bot.user.model.BundesLand

enum class PublicationStatus { ACTIVE, NOT_ACTIVE }

class RowMapper : (List<String>) -> Apartment? {

    private val logger = KotlinLogging.logger { }

    override fun invoke(source: List<String>): Apartment? {
        return kotlin.runCatching {
            Apartment(
                source.id,
                source.city,
                source.bundesLand.germanName,
                source.minTenants,
                source.maxTenants,
                source.description,
                source.petsAllowed,
                source.publicationStatus.name,
                source.etage,
                source.mapLocation,
                source.livingPeriod,
                source.showingDate
            )
        }.getOrElse {
            logger.debug { "Invalid spreadsheet entry, ${it.message}" }
            null
        }
    }

    private val List<String>.id: String
        get() = this[columnsMap.getValue(APARTMENT.ID)].takeIf { it.trim().isNotBlank() }
            ?: throw SheetValidationException.InvalidApartmentId(this[0].trim())
    private val List<String>.city: String
        get() = this[columnsMap.getValue(APARTMENT.CITY)].trim()
    private val List<String>.bundesLand: BundesLand
        get() = BundesLand.values()
            .firstOrNull { it.germanName == this[columnsMap.getValue(APARTMENT.BUNDESLAND)].trim() }
            ?: throw SheetValidationException.InvalidBundesLand(
                this[columnsMap.getValue(APARTMENT.BUNDESLAND)],
                this[columnsMap.getValue(APARTMENT.ID)]
            )
    private val List<String>.etage: String
        get() = kotlin.runCatching { this[columnsMap.getValue(APARTMENT.ETAGE)] }.getOrDefault("Не вказано")
    private val List<String>.minTenants: Short
        get() = runCatching { this[columnsMap.getValue(APARTMENT.MIN_TENANTS)].parseTenantsNum().first }
            .getOrDefault(1)
    private val List<String>.maxTenants: Short
        get() = runCatching { this[columnsMap.getValue(APARTMENT.MAX_TENANTS)].parseTenantsNum().second }
            .getOrDefault(10)
    private val List<String>.mapLocation: String
        get() = kotlin.runCatching { this[columnsMap.getValue(APARTMENT.MAP_LOCATION)] }.getOrDefault("Не вказано")
    private val List<String>.description: String
        get() = listOf(this[6], this[10]).joinToString("\n") { it.trim() }
    private val List<String>.livingPeriod: String
        get() = this[columnsMap.getValue(APARTMENT.LIVING_PERIOD)].trim()
    private val List<String>.showingDate: String
        get() = this[columnsMap.getValue(APARTMENT.SHOWING_DATE)].trim()
    private val List<String>.petsAllowed: Boolean
        get() = runCatching {
            this[columnsMap.getValue(APARTMENT.PETS_ALLOWED)].trim().lowercase() == "так" ||
                this[columnsMap.getValue(APARTMENT.PETS_ALLOWED)].trim().lowercase() == "за домовленістю"
        }.getOrElse { false }
    private val List<String>.publicationStatus: PublicationStatus
        get() = if (this[columnsMap.getValue(APARTMENT.PUBLICATIONSTATUS)].trim()
            .uppercase() == "TRUE"
        ) PublicationStatus.ACTIVE else PublicationStatus.NOT_ACTIVE

    private val columnsMap: Map<TableField<ApartmentRecord, out Any>, Int> = mapOf(
        APARTMENT.ID to 0,
        APARTMENT.CITY to 1,
        APARTMENT.BUNDESLAND to 3,
        APARTMENT.MAP_LOCATION to 4,
        APARTMENT.MIN_TENANTS to 5,
        APARTMENT.MAX_TENANTS to 5,
        APARTMENT.ETAGE to 7,
        APARTMENT.PETS_ALLOWED to 8,
        APARTMENT.LIVING_PERIOD to 6,
        APARTMENT.SHOWING_DATE to 11,
        APARTMENT.PUBLICATIONSTATUS to 16
    )

    private fun String.parseTenantsNum(): Pair<Short, Short> {
        val cleaned = this.filter { it.isDigit() || it == '-' }.split("-").map { it.toShort() }
        return when (cleaned.size) {
            1 -> cleaned.first() to cleaned.first()
            2 -> cleaned.first() to cleaned.last()
            else -> 1.toShort() to 10.toShort()
        }
    }
}
