package org.ua.wohnung.bot

import org.koin.core.context.startKoin
import org.koin.core.logger.PrintLogger
import org.koin.core.qualifier.named
import org.koin.fileProperties
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.generics.LongPollingBot
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession
import org.ua.wohnung.bot.configuration.persistenceModule
import org.ua.wohnung.bot.configuration.registeredUserFlow
import org.ua.wohnung.bot.configuration.userFlowModule
import org.ua.wohnung.bot.configuration.messageGatewayModule

fun main() {
    startKoin {
        printLogger()
        logger(PrintLogger())
        fileProperties("/secrets/secrets.properties")
        modules(
            persistenceModule,
            userFlowModule,
            registeredUserFlow,
            messageGatewayModule
        )
        koin.get<LongPollingBot>(named("WohnungsBot")).let { bot ->
            TelegramBotsApi(DefaultBotSession::class.java).registerBot(bot)
        }
    }
}
