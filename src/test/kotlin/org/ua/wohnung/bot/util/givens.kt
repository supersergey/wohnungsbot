package org.ua.wohnung.bot.util

import org.ua.wohnung.bot.persistence.generated.tables.pojos.Apartment
import org.ua.wohnung.bot.persistence.generated.tables.pojos.UserDetails
import org.ua.wohnung.bot.sheets.PublicationStatus
import org.ua.wohnung.bot.user.model.BundesLand
import kotlin.random.Random.Default.nextInt
import kotlin.random.Random.Default.nextLong

fun aFullUserDetails() = UserDetails(
    nextInt(),
    "John Smith",
    "12234",
    nextInt(1, 5).toShort(),
    true,
    BundesLand.values().random().germanName
)

fun aPartialUserDetails() = UserDetails(
    nextInt(),
    null,
    null,
    null,
    true,
    null
)

fun anApartment(
    id: Long = nextLong(),
    city: String = "city",
    bundesLand: BundesLand = BundesLand.values().random(),
    minTenants: Short = 1,
    maxTenants: Short = 5,
    description: String = "description",
    petsAllowed: Boolean = false,
    publicationStatus: PublicationStatus = PublicationStatus.ACTIVE
): Apartment {
    return Apartment(
        "$id",
        city,
        bundesLand.germanName,
        minTenants,
        maxTenants,
        description,
        petsAllowed,
        publicationStatus.name
    )
}
