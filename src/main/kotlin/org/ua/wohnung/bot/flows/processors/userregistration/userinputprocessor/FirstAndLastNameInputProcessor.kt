package org.ua.wohnung.bot.flows.processors.userregistration.userinputprocessor

import org.ua.wohnung.bot.flows.dto.ChatMetadata
import org.ua.wohnung.bot.flows.processors.UserInputProcessingResult
import org.ua.wohnung.bot.flows.processors.UserInputProcessor
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.user.UserService

class FirstAndLastNameInputProcessor(private val userService: UserService): UserInputProcessor {
    override val supportedStep: FlowStep = FlowStep.FIRSTNAME_AND_LASTNAME

    override fun invoke(chatMetadata: ChatMetadata): UserInputProcessingResult {
        if (chatMetadata.input.length !in (4..256)) {
            return UserInputProcessingResult.Error(
                message = "❌ Помилка, невірно введені дані. Будь-ласка, введіть своє імʼя і прізвище",
                finishSession = false
            )
        }
        userService.updateUserDetails(chatMetadata.userId) {
            firstLastName = chatMetadata.input
        }
        return UserInputProcessingResult.Success
    }
}