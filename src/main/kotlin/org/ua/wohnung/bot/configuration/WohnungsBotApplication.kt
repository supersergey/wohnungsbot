package org.ua.wohnung.bot.configuration

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jooq.SQLDialect
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent.getKoin
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.generics.LongPollingBot
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession
import org.ua.wohnung.bot.Session
import org.ua.wohnung.bot.UserRegistrationFlow
import org.ua.wohnung.bot.UserRegistrationFlowSteps
import org.ua.wohnung.bot.WohnungsBot
import org.ua.wohnung.bot.persistence.AccountRepository
import org.ua.wohnung.bot.persistence.configuration.JooqContextBuilder
import org.ua.wohnung.bot.security.Secrets
import org.ua.wohnung.bot.security.Secrets.*
import javax.sql.DataSource

class WohnungsBotApplication {
    fun registerBot(bot: LongPollingBot) {
        TelegramBotsApi(DefaultBotSession::class.java).registerBot(bot)
    }
}

val wohnungsBotModule = module {
    single<LongPollingBot> { WohnungsBot(getProperty(BOT_API_SECRET.setting), get(), get()) }
    single { UserRegistrationFlow(UserRegistrationFlowSteps.START_STEP) }
    singleOf(::Session)
    single<DataSource> {
        HikariDataSource(
            HikariConfig().apply {
                this.driverClassName = DRIVER_CLASS_NAME.asProperty()
                this.username = JDBC_USER.asProperty()
                this.password = JDBC_PASSWORD.asProperty()
                this.jdbcUrl = JDBC_URL.asProperty()
            }
        )
    }
    single {
        JooqContextBuilder(
            get(),
            SQLDialect.valueOf(SQL_DIALECT.asProperty())
        ).build()
    }

    single { AccountRepository(get()) }

    singleOf(::WohnungsBotApplication)

}