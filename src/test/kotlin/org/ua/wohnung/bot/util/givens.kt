package org.ua.wohnung.bot.util

import org.ua.wohnung.bot.persistence.generated.enums.Role
import org.ua.wohnung.bot.persistence.generated.tables.pojos.Account
import org.ua.wohnung.bot.persistence.generated.tables.pojos.Apartment
import org.ua.wohnung.bot.persistence.generated.tables.pojos.UserDetails
import org.ua.wohnung.bot.sheets.PublicationStatus
import org.ua.wohnung.bot.user.model.BundesLand
import kotlin.random.Random.Default.nextInt
import kotlin.random.Random.Default.nextLong

fun anAccount() = Account(
    nextLong(1, 100),
    nextLong(1, 100),
    "userName",
    Role.USER
)

fun aFullUserDetails(
    id: Long = nextLong(),
    bundesLand: BundesLand = BundesLand.values().random()
) = UserDetails(
    id,
    "John Smith",
    "12234",
    nextInt(1, 5).toShort(),
    true,
    bundesLand.germanName,
    "family members",
    "district",
    true,
    "foreign languages",
    "allergies",
    "abc@email.com",
    true,
    "wbs_details"
)

fun aPartialUserDetails(id: Long = nextLong()) = UserDetails(
    id,
    null,
    null,
    null,
    true,
    null,
    null,
    null,
    false,
    null,
    null,
    "email",
    null,
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
    publicationStatus: PublicationStatus = PublicationStatus.ACTIVE,
    etage: String = "1",
    mapLocation: String = "",
    livingPeriod: String = "",
    showingDate: String = "",
    wbs: Boolean = false,
    wbsDetails: String = "wbs_details"
): Apartment {
    return Apartment(
        "$id",
        city,
        bundesLand.germanName,
        minTenants,
        maxTenants,
        description,
        petsAllowed,
        publicationStatus.name,
        etage,
        mapLocation,
        livingPeriod,
        showingDate,
        wbs,
        wbsDetails
    )
}
