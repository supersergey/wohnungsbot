package org.ua.wohnung.bot.security

import java.nio.file.Files
import java.nio.file.Path

object SecretContainer {
    private val secretFile = this.javaClass.classLoader
        .getResource("secrets/bot_api_secret.txt")?.file
        ?: throw Exception("Unable to load BOT API secret")

    fun getApiToken(): String = Files.readString(Path.of(secretFile))
}