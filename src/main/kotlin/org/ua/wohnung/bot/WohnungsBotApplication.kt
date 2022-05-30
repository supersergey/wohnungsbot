package org.ua.wohnung.bot

import org.koin.core.context.startKoin
import org.koin.core.logger.PrintLogger
import org.koin.core.qualifier.named
import org.koin.fileProperties
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.generics.LongPollingBot
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession
import org.ua.wohnung.bot.configuration.wohnungsBotModule
import org.ua.wohnung.bot.flows.FlowInitializer

fun main() {
    startKoin {
        printLogger()
        logger(PrintLogger())
        modules(
            wohnungsBotModule
        )
        fileProperties("/secrets/secrets.properties")
        koin.get<FlowInitializer>(named("UserRegistrationFlowInitializer")).initialize()
        koin.get<LongPollingBot>(named("WohnungsBot")).let { bot ->
            TelegramBotsApi(DefaultBotSession::class.java).registerBot(bot)
        }
    }

}
