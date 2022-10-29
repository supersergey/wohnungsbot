package org.ua.wohnung.bot.flows.stringifiers

import org.ua.wohnung.bot.persistence.generated.tables.pojos.Account
import org.ua.wohnung.bot.persistence.generated.tables.pojos.UserDetails

fun UserDetails?.stringify(account: Account?) = this?.let {
    StringBuilder()
        .append("\uD83E\uDEAC Логін телеграм: ${account?.username?.let { username -> "https://t.me/$username" } ?: "Прихований"}")
        .append("\n\n")
        .append("\uD83E\uDEA7 Прізвище: ${it.firstLastName}")
        .append("\n\n")
        .append("\uD83D\uDCF1 Телефон: ${it.phone}")
        .append("\n\n")
        .append("\uD83D\uDC68\u200D\uD83D\uDC69\u200D\uD83D\uDC67\u200D\uD83D\uDC66 Кількість людей в родині: ${it.numberOfTenants}")
        .append("\n\n")
        .append("\uD83D\uDC68\u200D\uD83D\uDC69\u200D\uD83D\uDC67\u200D\uD83D\uDC66 Склад сімʼї: ${it.familyMembers}")
        .append("\n\n")
        .append("\uD83D\uDC36 Чи є тварини: ${if (it.pets) "так" else "ні"}")
        .append("\n\n")
        .append("\uD83D\uDDFA Де зареєстрований: ${it.bundesland}")
        .append("\n\n")
        .append("\uD83D\uDCCD Район: ${it.district}")
        .append("\n\n")
        .append("\uD83D\uDE98 Чи готові до переїзду: ${if (it.readyToMove) "так" else "ні"}")
        .append("\n\n")
        .append("\uD83C\uDFE7 Іноземні мови: ${it.foreignLanguages}")
        .append("\n\n")
        .append("\uD83C\uDF21 Алергії: ${it.allergies}")
        .append("\n\n")
        .toString()
} ?: "Користувач не знайдений"
