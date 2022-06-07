package org.ua.wohnung.bot.sheets

import com.google.api.services.sheets.v4.SheetsScopes
import com.google.auth.oauth2.GoogleCredentials
import com.google.auth.oauth2.ServiceAccountCredentials
import java.io.FileNotFoundException

class GoogleCredentialsProvider {
    fun get(): GoogleCredentials {
        return this::class.java.getResourceAsStream(CREDENTIALS_FILE_PATH)
            ?.use {
                ServiceAccountCredentials.fromStream(it)
                    .toBuilder()
                    .build()
                    .createScoped(SCOPES)
            } ?: throw FileNotFoundException("Resource not found: $CREDENTIALS_FILE_PATH")
    }

    companion object {
        private val SCOPES = listOf(SheetsScopes.SPREADSHEETS_READONLY)
        private const val CREDENTIALS_FILE_PATH = "/secrets/google-account-secret.json"
    }
}