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
import org.ua.wohnung.bot.flows.processors.UserInputProcessorsRegistry
import org.ua.wohnung.bot.flows.processors.registereduser.RegisteredUserFlow
import org.ua.wohnung.bot.flows.processors.userregistration.stepfactory.AcceptPoliciesStepFactory
import org.ua.wohnung.bot.flows.processors.userregistration.stepfactory.AllergiesStepFactory
import org.ua.wohnung.bot.flows.processors.userregistration.stepfactory.ApprovePersonalDataStepFactory
import org.ua.wohnung.bot.flows.processors.userregistration.stepfactory.BundeslandStepFactory
import org.ua.wohnung.bot.flows.processors.userregistration.stepfactory.ConversationFinishedDeclinedStepFactory
import org.ua.wohnung.bot.flows.processors.userregistration.stepfactory.ConversationStartStepFactory
import org.ua.wohnung.bot.flows.processors.userregistration.stepfactory.DistrictSelectionStepFactory
import org.ua.wohnung.bot.flows.processors.userregistration.stepfactory.DummyInitStepFactory
import org.ua.wohnung.bot.flows.processors.userregistration.stepfactory.FamilyCountStepFactory
import org.ua.wohnung.bot.flows.processors.userregistration.stepfactory.FamilyMembersStepFactory
import org.ua.wohnung.bot.flows.processors.userregistration.stepfactory.FirstAndLastNameStepFactory
import org.ua.wohnung.bot.flows.processors.userregistration.stepfactory.ForeignLanguagesStepFactory
import org.ua.wohnung.bot.flows.processors.userregistration.stepfactory.PetsStepFactory
import org.ua.wohnung.bot.flows.processors.userregistration.stepfactory.PhoneNumberStepFactory
import org.ua.wohnung.bot.flows.processors.userregistration.stepfactory.ReadyToMoveStepFactory
import org.ua.wohnung.bot.flows.processors.userregistration.stepfactory.StepFactoriesRegistry
import org.ua.wohnung.bot.flows.processors.userregistration.userinputprocessor.AcceptPoliciesUserInputProcessor
import org.ua.wohnung.bot.flows.processors.userregistration.userinputprocessor.AllergiesInputProcessor
import org.ua.wohnung.bot.flows.processors.userregistration.userinputprocessor.ApprovePersonalDataUserInputProcessor
import org.ua.wohnung.bot.flows.processors.userregistration.userinputprocessor.BundeslandInputProcessor
import org.ua.wohnung.bot.flows.processors.userregistration.userinputprocessor.ConversationStartInputProcessor
import org.ua.wohnung.bot.flows.processors.userregistration.userinputprocessor.DistrictInputProcessor
import org.ua.wohnung.bot.flows.processors.userregistration.userinputprocessor.FamilyMembersInputProcessor
import org.ua.wohnung.bot.flows.processors.userregistration.userinputprocessor.FirstAndLastNameInputProcessor
import org.ua.wohnung.bot.flows.processors.userregistration.userinputprocessor.ForeignLanguagesInputProcessor
import org.ua.wohnung.bot.flows.processors.userregistration.userinputprocessor.NumberOfTenantsInputProcessor
import org.ua.wohnung.bot.flows.processors.userregistration.userinputprocessor.PetsInputProcessor
import org.ua.wohnung.bot.flows.processors.userregistration.userinputprocessor.PhoneNumberInputProcessor
import org.ua.wohnung.bot.flows.processors.userregistration.userinputprocessor.ReadyToMoveInputProcessor
import org.ua.wohnung.bot.flows.step.UserRegistrationFlow
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

// val userFlowModule = module {
//    singleOf(::UserRegistrationFlow)
// }

val registeredUserFlow = module {
//    single {
//        DynamicButtonProducersRegistry(
//            DynamicButtonsProducerImpl(get())
//        )
//    }
//    single { RegisteredUserFlow(get()) }
}

// val adminModule = module {
//    singleOf(::AdminFlow)
// }

// val ownerModule = module {
//    singleOf(::OwnerFlow)
// }

val processorsModule = module {
//    single {
//        ProcessorContainer.PreProcessors(
//            UserDetailsStepProcessor.BundesLandSelectionStepProcessor(get()),
//            UserDetailsStepProcessor.UserRegistrationFlowConditionsRejectedStepProcessor(get())
//        )
//    }
//    single {
//        ProcessorContainer.PostProcessors(
//            UpdateUserDetailsStepProcessor.Bundesland(get()),
//            UpdateUserDetailsStepProcessor.DistrictSelectionStepProcessorUpdate(get()),
//            UpdateUserDetailsStepProcessor.FamilyCountStepProcessorUpdate(get()),
//            UpdateUserDetailsStepProcessor.FamilyMembersStepProcessorUpdate(get()),
//            UpdateUserDetailsStepProcessor.FirstAndLastNameStepProcessorUpdate(get()),
//            UpdateUserDetailsStepProcessor.PhoneNumberStepProcessorUpdate(get()),
//            UpdateUserDetailsStepProcessor.PetsStepProcessorUpdate(get()),
//            UpdateUserDetailsStepProcessor.ForeignLanguagesStepProcessorUpdate(get()),
//            UpdateUserDetailsStepProcessor.AllergiesStepProcessorUpdate(get()),
//            UpdateUserDetailsStepProcessor.ReadyToMoveStepProcessorUpdate(get()),
//
//            RegisteredUserStepProcessor.RegisteredUserRequestReceived(get()),
//
//            OwnerStepProcessor.AddAdmin(get(), get()),
//            OwnerStepProcessor.RemoveAdmin(get(), get())
//        )
//    }
//    single {
//        ProcessorContainer.MessagePreProcessors(
//            MessageStepProcessor.RegisteredUserConversationStart(get()),
//            MessageStepProcessor.RegisteredUserListApartments(get(), get()),
//
//            OwnerMessageStepProcessor.OwnerStart(get(), get()),
//            OwnerMessageStepProcessor.OwnerApartmentsUpdated(get()),
//            OwnerMessageStepProcessor.OwnerListAdmins(get()),
//
//            AdminMessageStepProcessor.AdminStart(get(), get()),
//            AdminMessageStepProcessor.AdminWhoIsInterested(get(), get())
//        )
//    }
}

val messageGatewayModule = module {
    single { Session() }
    single { MessageSource(get(), Path.of("flows", "newUserFlow.yml")) }
    singleOf(::MessageFactory)
    single {
        FlowRegistry(UserRegistrationFlow(), RegisteredUserFlow())
    }
    single {
        StepFactoriesRegistry(
            DummyInitStepFactory(get(), get()),
            ConversationStartStepFactory(get(), get()),
            AcceptPoliciesStepFactory(get(), get()),
            ApprovePersonalDataStepFactory(get(), get()),
            ConversationFinishedDeclinedStepFactory(get(), get()),
            BundeslandStepFactory(get(), get()),
            DistrictSelectionStepFactory(get(), get()),
            FamilyCountStepFactory(get(), get()),
            FamilyMembersStepFactory(get(), get()),
            FirstAndLastNameStepFactory(get(), get()),
            PhoneNumberStepFactory(get(), get()),
            PetsStepFactory(get(), get()),
            ForeignLanguagesStepFactory(get(), get()),
            ReadyToMoveStepFactory(get(), get()),
            AllergiesStepFactory(get(), get())
        )
    }
    single {
        UserInputProcessorsRegistry(
            ConversationStartInputProcessor(),
            AcceptPoliciesUserInputProcessor(get()),
            ApprovePersonalDataUserInputProcessor(get()),
            BundeslandInputProcessor(get()),
            DistrictInputProcessor(get()),
            FamilyMembersInputProcessor(get()),
            FirstAndLastNameInputProcessor(get()),
            ForeignLanguagesInputProcessor(get()),
            NumberOfTenantsInputProcessor(get()),
            PetsInputProcessor(get()),
            PhoneNumberInputProcessor(get()),
            ReadyToMoveInputProcessor(get()),
            AllergiesInputProcessor(get())
        )
    }
    single<LongPollingBot>(named("WohnungsBot")) {
        MessageGateway(
            getProperty(BOT_API_SECRET.setting),
            getProperty(Secrets.BOT_NAME.setting),
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
