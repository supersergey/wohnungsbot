package org.ua.wohnung.bot

import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.fileProperties
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.generics.LongPollingBot
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession
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
            ownerModule,
            processorsModule,
            messageGatewayModule,
            sheetReaderModule,
            servicesModule,
        )
        koin.get<LongPollingBot>(named("WohnungsBot")).let { bot ->
            TelegramBotsApi(DefaultBotSession::class.java).registerBot(bot)
        }
    }
}
