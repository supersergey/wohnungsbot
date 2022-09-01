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
import org.ua.wohnung.bot.account.AccountService
import org.ua.wohnung.bot.apartment.ApartmentService
import org.ua.wohnung.bot.flows.FlowRegistry
import org.ua.wohnung.bot.flows.admin.AdminFlow
import org.ua.wohnung.bot.flows.admin.AdminMessagePreProcessor
import org.ua.wohnung.bot.flows.dynamicbuttons.DynamicButtonProducersRegistry
import org.ua.wohnung.bot.flows.dynamicbuttons.DynamicButtonsProducerImpl
import org.ua.wohnung.bot.flows.owner.OwnerFlow
import org.ua.wohnung.bot.flows.owner.OwnerMessagePreProcessor
import org.ua.wohnung.bot.flows.owner.OwnerPostProcessor
import org.ua.wohnung.bot.flows.processors.MessagePreProcessor
import org.ua.wohnung.bot.flows.processors.ProcessorContainer
import org.ua.wohnung.bot.flows.registereduser.RegisteredUserFlow
import org.ua.wohnung.bot.flows.registereduser.RegisteredUserPostProcessor
import org.ua.wohnung.bot.flows.step.StepFactory
import org.ua.wohnung.bot.flows.userregistration.UpdateUserDetailsPostProcessor
import org.ua.wohnung.bot.flows.userregistration.UserDetailsPreProcessor
import org.ua.wohnung.bot.flows.userregistration.UserRegistrationFlow
import org.ua.wohnung.bot.gateway.MessageFactory
import org.ua.wohnung.bot.gateway.MessageGateway
import org.ua.wohnung.bot.gateway.Session
import org.ua.wohnung.bot.persistence.AccountRepository
import org.ua.wohnung.bot.persistence.ApartmentAccountRepository
import org.ua.wohnung.bot.persistence.ApartmentRepository
import org.ua.wohnung.bot.persistence.UserDetailsRepository
import org.ua.wohnung.bot.security.Secrets
import org.ua.wohnung.bot.security.Secrets.BOT_API_SECRET
import org.ua.wohnung.bot.security.Secrets.DRIVER_CLASS_NAME
import org.ua.wohnung.bot.security.Secrets.JDBC_PASSWORD
import org.ua.wohnung.bot.security.Secrets.JDBC_URL
import org.ua.wohnung.bot.security.Secrets.JDBC_USER
import org.ua.wohnung.bot.sheets.GoogleCredentialsProvider
import org.ua.wohnung.bot.sheets.RowMapper
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
    single { ApartmentAccountRepository(get()) }
}

val servicesModule = module {
    singleOf(::UserService)
    singleOf(::ApartmentService)
    singleOf(::AccountService)
}

val userFlowModule = module {
    single { UserRegistrationFlow(get()) }
}

val registeredUserFlow = module {
    single {
        DynamicButtonProducersRegistry(
            DynamicButtonsProducerImpl(get())
        )
    }
    single { RegisteredUserFlow(get()) }
}

val adminModule = module {
    single { AdminFlow(get()) }
}

val ownerModule = module {
    single { OwnerFlow(get()) }
}

val processorsModule = module {
    single {
        ProcessorContainer.PreProcessors(
            UserDetailsPreProcessor.BundesLandSelectionPreProcessor(get()),
            UserDetailsPreProcessor.UserRegistrationFlowConditionsRejectedPreProcessor(get())
        )
    }
    single {
        ProcessorContainer.PostProcessors(
            UpdateUserDetailsPostProcessor.Bundesland(get()),
            UpdateUserDetailsPostProcessor.DistrictSelectionPostProcessorUpdate(get()),
            UpdateUserDetailsPostProcessor.FamilyCountPostProcessorUpdate(get()),
            UpdateUserDetailsPostProcessor.FamilyMembersPostProcessorUpdate(get()),
            UpdateUserDetailsPostProcessor.FirstAndLastNamePostProcessorUpdate(get()),
            UpdateUserDetailsPostProcessor.PhoneNumberPostProcessorUpdate(get()),
            UpdateUserDetailsPostProcessor.EmailPostProcessorUpdate(get()),
            UpdateUserDetailsPostProcessor.PetsPostProcessorUpdate(get()),
            UpdateUserDetailsPostProcessor.ForeignLanguagesPostProcessorUpdate(get()),
            UpdateUserDetailsPostProcessor.AllergiesPostProcessorUpdate(get()),
            UpdateUserDetailsPostProcessor.ReadyToMovePostProcessorUpdate(get()),

            RegisteredUserPostProcessor.RegisteredUserRequestReceived(get()),

            OwnerPostProcessor.AddAdmin(get(), get()),
            OwnerPostProcessor.RemoveAdmin(get(), get())
        )
    }
    single {
        ProcessorContainer.MessagePreProcessors(
            MessagePreProcessor.RegisteredUserConversationStart(get()),
            MessagePreProcessor.RegisteredUserListApartments(get(), get()),

            OwnerMessagePreProcessor.OwnerStart(get(), get()),
            OwnerMessagePreProcessor.OwnerApartmentsUpdated(get()),
            OwnerMessagePreProcessor.OwnerListAdmins(get()),

            AdminMessagePreProcessor.AdminStart(get(), get()),
            AdminMessagePreProcessor.AdminWhoIsInterested(get(), get())
        )
    }
}

val messageGatewayModule = module {
    single { Session() }
    single { MessageSource(get(), Path.of("flows", "newUserFlow.yml")) }
    single { StepFactory(get(), get(), get()) }
    singleOf(::MessageFactory)
    single {
        FlowRegistry(
            get(),
            get<UserRegistrationFlow>(),
            get<OwnerFlow>(),
            get<AdminFlow>(),
            get<RegisteredUserFlow>()
        )
    }
    single<LongPollingBot>(named("WohnungsBot")) {
        MessageGateway(
            getProperty(BOT_API_SECRET.setting),
            getProperty(Secrets.BOT_NAME.setting),
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
    singleOf(::RowMapper)
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
