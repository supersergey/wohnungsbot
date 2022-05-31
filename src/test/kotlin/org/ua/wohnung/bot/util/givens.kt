package org.ua.wohnung.bot.util

import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils
import org.ua.wohnung.bot.persistence.generated.tables.pojos.UserDetails
import org.ua.wohnung.bot.user.model.BundesLand
import kotlin.random.Random.Default.nextInt

fun aFullUserDetails() = UserDetails(
    RandomStringUtils.randomAlphabetic(10),
    "12234",
    nextInt(1, 5).toShort(),
    true,
    BundesLand.values().random().germanName
)

fun aPartialUserDetails() = UserDetails(
    RandomStringUtils.randomAlphabetic(10),
    null,
    null,
    true,
    null
)