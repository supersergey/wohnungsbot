package org.ua.wohnung.bot.flows.processors.userregistration.userinputprocessor

import org.ua.wohnung.bot.flows.dto.ChatMetadata
import org.ua.wohnung.bot.flows.processors.UserInputProcessingResult
import org.ua.wohnung.bot.flows.processors.UserInputProcessor
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.user.UserService

class NumberOfTenantsInputProcessor(private val userService: UserService): UserInputProcessor {
    override val supportedStep: FlowStep = FlowStep.FAMILY_COUNT

    override fun invoke(chatMetadata: ChatMetadata): UserInputProcessingResult {
        if (chatMetadata.input !in (1..12).map { "$it" }) {
            return UserInputProcessingResult.Error(
                "❌ Помилка, невірно введені дані. Вкажіть розмір родини, скориставшись кнопками",
                finishSession = false
            )
        }
        userService.updateUserDetails(chatMetadata.userId) {
            numberOfTenants = chatMetadata.input.toShort()
        }
        return UserInputProcessingResult.Success
    }
}