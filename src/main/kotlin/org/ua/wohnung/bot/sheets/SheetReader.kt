package org.ua.wohnung.bot.sheets

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.sheets.v4.Sheets
import com.google.auth.http.HttpCredentialsAdapter

class SheetReader(
    credentialsProvider: GoogleCredentialsProvider,
    private val sheetProperties: SheetProperties
) {
    private val jsonFactory = GsonFactory.getDefaultInstance()
    private val applicationName = "ua-wohnungs-bot"
    private val httpTransport = GoogleNetHttpTransport.newTrustedTransport()
    private val service = Sheets.Builder(httpTransport, jsonFactory, HttpCredentialsAdapter(credentialsProvider.get()))
        .setApplicationName(applicationName)
        .build()

    @Suppress("UNCHECKED_CAST")
    fun readRows(): List<List<String>> {
        return (
            service.spreadsheets().values()
                .get(sheetProperties.spreadsheetId, sheetProperties.range)
                .execute().getValues() as List<List<String>>
            )
    }
}
