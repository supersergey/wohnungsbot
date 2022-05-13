package org.ua.wohnung.bot

import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession
import org.ua.wohnung.bot.security.SecretContainer

class WohnungsbotApplication

fun main() {
    val userRegistrationFlow = UserRegistrationFlow(UserRegistrationFlowSteps.START_STEP).apply {
        add(UserRegistrationFlowSteps.PERSONAL_DATA_STEP)
        add(UserRegistrationFlowSteps.LAND_SELECTION_STEP)
        add(UserRegistrationFlowSteps.NUMBER_OF_TENANTS_STEP)
        add(UserRegistrationFlowSteps.PETS_STEP)
        add(UserRegistrationFlowSteps.FAILED_STEP)
        add(UserRegistrationFlowSteps.SUCCESS_STEP)
    }

    TelegramBotsApi(DefaultBotSession::class.java).registerBot(
        WohnungsBot(SecretContainer, userRegistrationFlow, Session)
    )
}
