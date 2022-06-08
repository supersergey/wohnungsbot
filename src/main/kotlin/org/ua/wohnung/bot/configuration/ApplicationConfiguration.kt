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
import org.ua.wohnung.bot.flows.FlowRegistry
import org.ua.wohnung.bot.flows.StepFactory
import org.ua.wohnung.bot.flows.processors.MessagePreProcessor
import org.ua.wohnung.bot.flows.processors.ProcessorContainer
import org.ua.wohnung.bot.flows.processors.RegisteredUserPreProcessor
import org.ua.wohnung.bot.flows.processors.UpdateUserDetailsPostProcessor
import org.ua.wohnung.bot.flows.processors.UserDetailsPreProcessor
import org.ua.wohnung.bot.flows.registereduser.RegisteredUserFlow
import org.ua.wohnung.bot.flows.userregistration.UserRegistrationFlow
import org.ua.wohnung.bot.gateway.MessageFactory
import org.ua.wohnung.bot.gateway.MessageGateway
import org.ua.wohnung.bot.gateway.Session
import org.ua.wohnung.bot.persistence.AccountRepository
import org.ua.wohnung.bot.persistence.ApartmentRepository
import org.ua.wohnung.bot.persistence.UserDetailsRepository
import org.ua.wohnung.bot.security.Secrets.BOT_API_SECRET
import org.ua.wohnung.bot.security.Secrets.DRIVER_CLASS_NAME
import org.ua.wohnung.bot.security.Secrets.JDBC_PASSWORD
import org.ua.wohnung.bot.security.Secrets.JDBC_URL
import org.ua.wohnung.bot.security.Secrets.JDBC_USER
import org.ua.wohnung.bot.sheets.GoogleCredentialsProvider
import org.ua.wohnung.bot.sheets.SheetProperties
import org.ua.wohnung.bot.sheets.SheetReader
import org.ua.wohnung.bot.user.UserService
import java.nio.file.Path
import javax.sql.DataSource

val commonModule = module {
    yamlObjectMapper()
}

val persistenceModule = module {
    datasource()
    jooq()
    single { AccountRepository(get()) }
    single { ApartmentRepository(get()) }
    single { UserDetailsRepository(get()) }
    single { UserService(get(), get(), get()) }
}

val userFlowModule = module {
    single { UserRegistrationFlow(get()) }
    single {
        ProcessorContainer.PreProcessors(
            UserDetailsPreProcessor.BundesLandSelectionPreProcessor(get()),
            RegisteredUserPreProcessor.UserRegistrationFlowConditionsRejectedPreProcessor(get())
        )
    }
    single {
        ProcessorContainer.PostProcessors(
            UpdateUserDetailsPostProcessor.Bundesland(get()),
            UpdateUserDetailsPostProcessor.FamilyCountPostProcessorUpdate(get()),
            UpdateUserDetailsPostProcessor.FirstAndLastNamePostProcessorUpdate(get()),
            UpdateUserDetailsPostProcessor.PhoneNumberPostProcessorUpdate(get()),
            UpdateUserDetailsPostProcessor.PetsPostProcessorUpdate(get())
        )
    }
    single {
        ProcessorContainer.MessagePreProcessors(
            MessagePreProcessor.RegisteredUserConversationStartPreProcessor(get())
        )
    }
}

val registeredUserFlow = module {
    single { RegisteredUserFlow(get()) }
}

val messageGatewayModule = module {
    singleOf(::Session)
    single() { MessageSource(get(), Path.of("flows", "newUserFlow.yml")) }
    single { StepFactory(get(), get(), get()) }
    singleOf(::MessageFactory)
    single { FlowRegistry(get(), get<UserRegistrationFlow>(), get<RegisteredUserFlow>()) }
    single<LongPollingBot>(named("WohnungsBot")) {
        MessageGateway(
            getProperty(BOT_API_SECRET.setting),
            get(),
            get(),
            get(),
            get()
        )
    }
}

val sheetReaderModule = module {
    single { SheetProperties(get(), Path.of("sheets", "sheet-properties.yml")) }
    singleOf(::GoogleCredentialsProvider)
    single { SheetReader(get(), get()) }
}

private fun Module.jooq() = single { DSL.using(get<DataSource>(), SQLDialect.POSTGRES) }

private fun Module.yamlObjectMapper() = single<ObjectMapper> {
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

private fun Module.datasource() = single<DataSource> {
    HikariDataSource(
        HikariConfig().apply {
            this.driverClassName = DRIVER_CLASS_NAME.getProperty()
            this.username = JDBC_USER.getProperty()
            this.password = JDBC_PASSWORD.getProperty()
            this.jdbcUrl = JDBC_URL.getProperty()
        }
    )
}
