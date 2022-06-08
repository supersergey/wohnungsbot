package org.ua.wohnung.bot.sheets

import org.ua.wohnung.bot.exception.SheetValidationException
import org.ua.wohnung.bot.exception.UserInputValidationException
import org.ua.wohnung.bot.user.model.BundesLand

enum class PublicationStatus { ACTIVE, NOT_ACTIVE }

data class Row(
    val id: String,
    val city: String,
    val bundesLand: BundesLand,
    val tenants: IntRange,
    val description: String,
    val petsAllowed: Boolean,
    val publicationStatus: PublicationStatus
)

class RowMapper : (List<String>) -> Row {
    override fun invoke(source: List<String>): Row {
        return Row(
            id = source.id,
            city = source.city,
            bundesLand = source.bundesLand,
            tenants = source.tenants,
            description = source.description,
            petsAllowed = source.petsAllowed,
            publicationStatus = source.publicationStatus
        )
    }

    private val List<String>.id: String
        get() = this[0]
    private val List<String>.city: String
        get() = this[1]
    private val List<String>.bundesLand: BundesLand
        get() = BundesLand.values().firstOrNull { it.germanName == this[2] }
            ?: throw UserInputValidationException.InvalidBundesLand(this[2])
    private val List<String>.tenants: IntRange
        get() = IntRange(
            runCatching { this[5].toInt() }.getOrElse { throw SheetValidationException.InvalidTenantsCount(this[5]) },
            runCatching { this[6].toInt() }.getOrElse { throw SheetValidationException.InvalidTenantsCount(this[6]) }
        )
    private val List<String>.description: String
        get() = listOf(this[7], this[8], this[12]).filterNot { it.isBlank() }.joinToString("\n\n")
    private val List<String>.petsAllowed: Boolean
        get() = runCatching {
            this[9] == "так"
        }.getOrElse { false }
    private val List<String>.publicationStatus: PublicationStatus
        get() = if (this[17] == "TRUE") PublicationStatus.ACTIVE else PublicationStatus.NOT_ACTIVE
}
