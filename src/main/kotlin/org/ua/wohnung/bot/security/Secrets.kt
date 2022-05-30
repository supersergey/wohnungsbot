package org.ua.wohnung.bot.security

import java.lang.System.getProperty

enum class Secrets(val setting: String) {
    JDBC_PASSWORD("jdbc_password"),
    JDBC_USER("jdbc_user"),
    JDBC_URL("jdbc_url"),
    DRIVER_CLASS_NAME("driver_class_name"),
    SQL_DIALECT("sql_dialect"),
    BOT_API_SECRET("bot_api_secret");

    fun getProperty(): String = getProperty(setting)
}
