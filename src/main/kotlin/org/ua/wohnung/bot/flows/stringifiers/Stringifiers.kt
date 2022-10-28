package org.ua.wohnung.bot.flows.stringifiers

import org.ua.wohnung.bot.persistence.generated.tables.pojos.Account
import org.ua.wohnung.bot.persistence.generated.tables.pojos.Apartment
import org.ua.wohnung.bot.persistence.generated.tables.pojos.UserDetails
import org.ua.wohnung.bot.sheets.PublicationStatus

fun UserDetails?.stringify(account: Account?) = this?.let {
    val toString = StringBuilder()
        .append("\uD83E\uDEAC Логін телеграм: ${account?.username?.let { username -> "https://t.me/$username" } ?: "Прихований"}")
        .append("\n\n")
        .append("\uD83E\uDEA7 Прізвище: $firstLastName")
        .append("\n\n")
        .append("\uD83D\uDCF1 Телефон: $phone")
        .append("\n\n")
        .append("✉️ Email: ${email ?: UNDEFINED}")
        .append("\n\n")
        .append("\uD83D\uDC68\u200D\uD83D\uDC69\u200D\uD83D\uDC67\u200D\uD83D\uDC66 Кількість людей в родині: $numberOfTenants")
        .append("\n\n")
        .append("\uD83D\uDC68\u200D\uD83D\uDC69\u200D\uD83D\uDC67\u200D\uD83D\uDC66 Склад сімʼї: $familyMembers")
        .append("\n\n")
        .append("\uD83D\uDC36 Чи є тварини: ${if (pets) "так" else "ні"}")
        .append("\n\n")
        .append("\uD83D\uDDFA Де зареєстрований: $bundesland")
        .append("\n\n")
        .append("\uD83D\uDCCD Район: $district")
        .append("\n\n")
        .append("\uD83D\uDE98 Чи готові до переїзду: ${if (readyToMove) "так" else "ні"}")
        .append("\n\n")
        .append("\uD83C\uDFE7 Іноземні мови: $foreignLanguages")
        .append("\n\n")
        .append("\uD83C\uDF21 Алергії: $allergies")
        .append("\n\n")
        .toString()
    toString
} ?: "Користувач не знайдений"

fun Apartment?.stringify(includeAdminFields: Boolean = false): String {
    if (this == null) return "Помешкання не знайдено"
    val fields = mutableMapOf(
        "Житло ✅" to id,
        "\uD83D\uDC49 Земля" to bundesland,
        "\uD83D\uDDFA Місто" to city,
        "\uD83D\uDC69\uD83D\uDC68\u200D\uD83E\uDDB1 Кількість людей" to "від $minTenants до $maxTenants",
        "\uD83D\uDC08\uD83D\uDC15" to if (petsAllowed) "Можна з тваринами" else "Без тварин",
        "\uD83C\uDFD8 Додаткова інформація" to description,
        "⬆️ Поверх " to (etage ?: UNDEFINED),
        "\uD83D\uDCCD Місцезнаходження на карті " to (mapLocation ?: UNDEFINED),
        "⏰ Термін проживання" to (livingPeriod?.takeIf { it.isNotBlank() } ?: UNDEFINED),
        "\uD83D\uDCC5 Дата показу житла" to (showingDate?.takeIf { it.isNotBlank() } ?: UNDEFINED)
    )
    if (includeAdminFields) {
        fields["\uD83D\uDD0D Статус житла"] =
            (if (publicationstatus == PublicationStatus.ACTIVE.name) "✅ активне" else "\uD83D\uDEAB деактивоване")
    }
    return fields.map { "${it.key} : ${it.value}"}.joinToString("\n\n")
}

const val UNDEFINED = "Не зазначений"