package org.ua.wohnung.bot

import org.flywaydb.core.Flyway
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.fileProperties
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.generics.LongPollingBot
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession
import org.ua.wohnung.bot.configuration.adminModule
import org.ua.wohnung.bot.configuration.commonModule
import org.ua.wohnung.bot.configuration.messageGatewayModule
import org.ua.wohnung.bot.configuration.ownerModule
import org.ua.wohnung.bot.configuration.persistenceModule
import org.ua.wohnung.bot.configuration.processorsModule
import org.ua.wohnung.bot.configuration.registeredUserFlow
import org.ua.wohnung.bot.configuration.servicesModule
import org.ua.wohnung.bot.configuration.sheetReaderModule
import org.ua.wohnung.bot.configuration.userFlowModule

fun main() {
    startKoin {
        printLogger()
        fileProperties("/secrets/secrets.properties")
        modules(
            commonModule,
            persistenceModule,
            userFlowModule,
            registeredUserFlow,
            adminModule,
            ownerModule,
            processorsModule,
            messageGatewayModule,
            sheetReaderModule,
            servicesModule,
        )
        flywayMigrate()
        koin.get<LongPollingBot>(named("WohnungsBot")).let { bot ->
            TelegramBotsApi(DefaultBotSession::class.java).registerBot(bot)
        }
    }
}

private fun KoinApplication.flywayMigrate() {
    Flyway.configure().dataSource(koin.get())
        .schemas("main")
        .createSchemas(true)
        .load()
        .run {
            migrate()
        }
}
