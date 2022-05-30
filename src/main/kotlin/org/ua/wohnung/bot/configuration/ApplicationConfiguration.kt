package org.ua.wohnung.bot.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.telegram.telegrambots.meta.generics.LongPollingBot
import org.ua.wohnung.bot.Session
import org.ua.wohnung.bot.WohnungsBot
import org.ua.wohnung.bot.flows.Flow
import org.ua.wohnung.bot.flows.FlowInitializer
import org.ua.wohnung.bot.flows.StepFactory
import org.ua.wohnung.bot.flows.UserRegistrationFlow
import org.ua.wohnung.bot.flows.UserRegistrationFlowInitializer
import org.ua.wohnung.bot.persistence.AccountRepository
import org.ua.wohnung.bot.security.Secrets.BOT_API_SECRET
import org.ua.wohnung.bot.security.Secrets.DRIVER_CLASS_NAME
import org.ua.wohnung.bot.security.Secrets.JDBC_PASSWORD
import org.ua.wohnung.bot.security.Secrets.JDBC_URL
import org.ua.wohnung.bot.security.Secrets.JDBC_USER
import java.nio.file.Path
import javax.sql.DataSource

val wohnungsBotModule = module {
    yamlObjectMapper()
    single { MessageSource(get(), Path.of("flows", "userflow.yml")) }
    single<Flow> { UserRegistrationFlow() }
    single { StepFactory(get(), get()) }
    single<FlowInitializer>(named("UserRegistrationFlowInitializer")) {
        UserRegistrationFlowInitializer(get())
    }
    single<LongPollingBot>(named("WohnungsBot")) { WohnungsBot(getProperty(BOT_API_SECRET.setting), get(), get()) }

    singleOf(::Session)
    datasource()
    jooq()
    single { AccountRepository(get()) }
}

fun Module.jooq() = single { DSL.using(get<DataSource>(), SQLDialect.POSTGRES) }

fun Module.yamlObjectMapper() = single<ObjectMapper> {
    ObjectMapper(YAMLFactory()).registerModule(
        KotlinModule.Builder()
            .withReflectionCacheSize(512)
            .configure(KotlinFeature.NullToEmptyCollection, false)
            .configure(KotlinFeature.NullToEmptyMap, false)
            .configure(KotlinFeature.NullIsSameAsDefault, false)
            .configure(KotlinFeature.SingletonSupport, false)
            .configure(KotlinFeature.StrictNullChecks, false)
            .build()
    )
}

fun Module.datasource() = single<DataSource> {
    HikariDataSource(
        HikariConfig().apply {
            this.driverClassName = DRIVER_CLASS_NAME.getProperty()
            this.username = JDBC_USER.getProperty()
            this.password = JDBC_PASSWORD.getProperty()
            this.jdbcUrl = JDBC_URL.getProperty()
        }
    )
}
