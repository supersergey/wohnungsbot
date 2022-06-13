package org.ua.wohnung.bot.sheets

import mu.KotlinLogging
import org.ua.wohnung.bot.exception.SheetValidationException
import org.ua.wohnung.bot.exception.UserInputValidationException
import org.ua.wohnung.bot.persistence.generated.tables.pojos.Apartment
import org.ua.wohnung.bot.user.model.BundesLand

enum class PublicationStatus { ACTIVE, NOT_ACTIVE }

class RowMapper : (List<String>) -> Apartment? {

    private val logger = KotlinLogging.logger {  }

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
                source.publicationStatus.name
            )
        }.getOrElse {
            logger.info { "Invalid spreadsheet entry, ${it.message}" }
            null
        }
    }

    private val List<String>.id: String
        get() = this[0].takeIf { it.isNotBlank() }
            ?: throw SheetValidationException.InvalidApartmentId(this[0])
    private val List<String>.city: String
        get() = this[1]
    private val List<String>.bundesLand: BundesLand
        get() = BundesLand.values().firstOrNull { it.germanName == this[2] }
            ?: throw UserInputValidationException.InvalidBundesLand(this[2])
    private val List<String>.minTenants: Short
        get() = runCatching { this[5].toShort() }.getOrDefault(1)
    private val List<String>.maxTenants: Short
        get() = runCatching { this[6].toShort() }.getOrDefault(10)
    private val List<String>.description: String
        get() = listOf(this[7], this[8], this[12]).filterNot { it.isBlank() }.joinToString("\n\n")
    private val List<String>.petsAllowed: Boolean
        get() = runCatching {
            this[9] == "так" || this[9] == "можливо"
        }.getOrElse { false }
    private val List<String>.publicationStatus: PublicationStatus
        get() = if (this[17] == "TRUE") PublicationStatus.ACTIVE else PublicationStatus.NOT_ACTIVE
}
