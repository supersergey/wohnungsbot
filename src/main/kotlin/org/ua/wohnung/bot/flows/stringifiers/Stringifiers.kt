package org.ua.wohnung.bot.flows.stringifiers

import org.ua.wohnung.bot.persistence.generated.tables.pojos.Account
import org.ua.wohnung.bot.persistence.generated.tables.pojos.Apartment
import org.ua.wohnung.bot.persistence.generated.tables.pojos.UserDetails
import org.ua.wohnung.bot.sheets.PublicationStatus

fun UserDetails?.stringify(account: Account?): String {
    if (this == null) return "Дані про користувача не знайдено"
    return mapOf(
        "\uD83E\uDEAC Логін телеграм" to (
            account?.username?.let { username -> "https://t.me/$username" } ?: "Прихований"
            ),
        "\uD83E\uDEA7 Прізвище" to firstLastName,
        "\uD83D\uDCF1 Телефон" to phone,
        "✉️ Email" to (email ?: UNDEFINED),
        "\uD83D\uDC68\u200D\uD83D\uDC69\u200D\uD83D\uDC67\u200D\uD83D\uDC66 Кількість людей в родині" to "$numberOfTenants",
        "\uD83D\uDC68\u200D\uD83D\uDC69\u200D\uD83D\uDC67\u200D\uD83D\uDC66 Склад сімʼї" to familyMembers,
        "\uD83D\uDC36 Чи є тварини" to (if (pets) "так" else "ні"),
        "\uD83D\uDDFA Де зареєстрований" to bundesland,
        "\uD83D\uDCCD Район" to district,
        "\uD83C\uDFD8 WBS:" to (
            if (wbs == null)
                UNDEFINED
            else if (wbs)
                "так, на кількість кімнат ${wbsNumberOfRooms ?: UNDEFINED}, ${wbsDetails ?: UNDEFINED}"
            else
                "ні"
            ),
        "Чи готові до переїзду" to (if (readyToMove) "так" else "ні"),
        "\uD83C\uDFE7 Іноземні мови" to foreignLanguages,
        "\uD83C\uDF21 Алергії" to allergies
    ).stringify()
}

fun Apartment?.stringify(includeAdminFields: Boolean = false): String {
    if (this == null) return "Помешкання не знайдено"
    val fields = mutableMapOf(
        "Житло ✅" to id,
        "\uD83D\uDC49 Земля" to bundesland,
        "\uD83D\uDDFA Місто" to city,
        "\uD83D\uDCDD Чи потрібен WBS" to listOfNotNull(
            if (wbs == true) "так" else "ні",
            wbsDetails?.ifBlank { null }
        ).joinToString(", "),
        "\uD83C\uDFE0 Кімнат" to "$numberOfRooms",
        "\uD83D\uDC69\uD83D\uDC68\u200D\uD83E\uDDB1 Кількість людей" to "від $minTenants до $maxTenants",
        "\uD83D\uDC08\uD83D\uDC15" to if (petsAllowed) "Можна з тваринами" else "Без тварин",
        "\uD83C\uDFD8 Додаткова інформація" to description,
        "⬆️ Поверх " to (etage ?: UNDEFINED),
        "\uD83D\uDCCD Місцезнаходження на карті " to (mapLocation ?: UNDEFINED),
        "\uD83D\uDCC5 Дата показу житла" to (showingDate?.takeIf { it.isNotBlank() } ?: UNDEFINED)
    )
    if (includeAdminFields) {
        fields["\uD83D\uDD0D Статус житла"] =
            (if (publicationstatus == PublicationStatus.ACTIVE.name) "✅ активне" else "\uD83D\uDEAB деактивоване")
    }
    return fields.stringify()
}

fun Map<String, String>.stringify(): String {
    return map { "${it.key} : ${it.value}" }.joinToString("\n\n")
}

const val UNDEFINED = "Не зазначений"
