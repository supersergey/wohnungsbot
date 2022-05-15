package org.ua.wohnung.bot.configuration

import org.jooq.ConnectionProvider
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.generics.LongPollingBot
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession
import org.ua.wohnung.bot.Session
import org.ua.wohnung.bot.UserRegistrationFlow
import org.ua.wohnung.bot.UserRegistrationFlowSteps
import org.ua.wohnung.bot.WohnungsBot
import org.ua.wohnung.bot.persistence.AccountRepository
import org.ua.wohnung.bot.persistence.configuration.JdbcConnectionProvider
import org.ua.wohnung.bot.persistence.configuration.JooqContextBuilder
import org.ua.wohnung.bot.security.Secrets
import org.ua.wohnung.bot.security.Secrets.*

class WohnungsBotApplication {
    fun registerBot(bot: LongPollingBot) {
        TelegramBotsApi(DefaultBotSession::class.java).registerBot(bot)
    }
}

val wohnungsBotModule = module {
    single<LongPollingBot> { WohnungsBot(getProperty(BOT_API_SECRET.setting), get(), get()) }
    single { UserRegistrationFlow(UserRegistrationFlowSteps.START_STEP) }
    singleOf(::Session)
    single<ConnectionProvider> {
        JdbcConnectionProvider(
            requireNotNull(getKoin().getProperty(JDBC_URL.setting)),
            requireNotNull(getKoin().getProperty(JDBC_USER.setting)),
            requireNotNull(getKoin().getProperty(JDBC_PASSWORD.setting))
        )
    }
    single {
        JooqContextBuilder(
            get(),
            requireNotNull(getKoin().getProperty(SQL_DIALECT.setting))
        ).build()
    }

    single { AccountRepository(get()) }

    singleOf(::WohnungsBotApplication)
}