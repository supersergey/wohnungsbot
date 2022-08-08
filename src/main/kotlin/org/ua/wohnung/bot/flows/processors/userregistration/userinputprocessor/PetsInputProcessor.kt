package org.ua.wohnung.bot.flows.processors.userregistration.userinputprocessor

import org.ua.wohnung.bot.flows.dto.ChatMetadata
import org.ua.wohnung.bot.flows.processors.Message
import org.ua.wohnung.bot.flows.processors.StepOutput
import org.ua.wohnung.bot.flows.processors.UserInputProcessingResult
import org.ua.wohnung.bot.flows.processors.UserInputProcessor
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.user.UserService

class PetsInputProcessor(private val userService: UserService): UserInputProcessor {
    override val supportedStep: FlowStep = FlowStep.PETS

    override fun invoke(chatMetadata: ChatMetadata): UserInputProcessingResult {
        if (chatMetadata.input !in setOf("так", "ні")) {
            return UserInputProcessingResult.Error(
                message = "❌ Помилка, невірно введені дані. Будь-ласка, вкажіть, чи маєте ви домашніх тварин. Скористайтесь кнопками",
                finishSession = false
            )
        }
        userService.updateUserDetails(chatMetadata.userId) {
            pets = chatMetadata.input == "так"
        }
        return UserInputProcessingResult.Success
    }
}