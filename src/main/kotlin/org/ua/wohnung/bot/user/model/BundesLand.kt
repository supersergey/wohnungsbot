package org.ua.wohnung.bot.user.model

enum class BundesLand(val germanName: String, val ukrainianName: String = "") {
    BERLIN("Berlin"),
    BAYERN("Bayern", "Баварія"),
    NIEDERSACHSEN("Niedersachsen", "Нижня Саксонія"),
    BADEN_WURTTEMBERG("Baden-Württemberg"),
    RHEINLAND_PFALZ("Rheinland-Pfalz"),
    SACHSEN("Sachsen", "Саксонія"),
    THURINGEN("Thüringen", "Тюрінгія"),
    HESSEN("Hessen"),
    NORDRHEIN_WESTFALEN("Nordrhein-Westfalen", "Північна Рейн-Вестфалія"),
    SACHSEN_ANHALT("Sachsen-Anhalt", "Саксонія Анхальт"),
    BRANDENBURG("Brandenburg"),
    MECKLENBURG_VORPOMMERN("Mecklenburg-Vorpommern"),
    HAMBURG("Hamburg"),
    SCHLESWIG_HOLSTEIN("Schleswig-Holstein"),
    SAARLAND("Saarland"),
    BREMEN("Bremen")
}