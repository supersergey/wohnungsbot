package org.ua.wohnung.bot

import org.koin.core.context.startKoin
import org.koin.core.logger.PrintLogger
import org.koin.fileProperties
import org.ua.wohnung.bot.configuration.WohnungsBotApplication
import org.ua.wohnung.bot.configuration.wohnungsBotModule

fun main() {
    val app = startKoin {
        printLogger()
        logger(PrintLogger())
        modules(
            wohnungsBotModule
        )
        fileProperties("/secrets/secrets.properties")
    }
    app.koin.get<UserRegistrationFlow>().apply {
        add(UserRegistrationFlowSteps.PERSONAL_DATA_STEP)
        add(UserRegistrationFlowSteps.LAND_SELECTION_STEP)
        add(UserRegistrationFlowSteps.NUMBER_OF_TENANTS_STEP)
        add(UserRegistrationFlowSteps.PETS_STEP)
        add(UserRegistrationFlowSteps.FAILED_STEP)
        add(UserRegistrationFlowSteps.SUCCESS_STEP)
    }
    app.koin.get<WohnungsBotApplication>().registerBot(app.koin.get())
}
