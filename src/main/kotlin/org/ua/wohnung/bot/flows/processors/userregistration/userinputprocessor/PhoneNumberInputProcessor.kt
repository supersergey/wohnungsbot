package org.ua.wohnung.bot.flows.processors.userregistration.userinputprocessor

import org.ua.wohnung.bot.flows.dto.ChatMetadata
import org.ua.wohnung.bot.flows.processors.Message
import org.ua.wohnung.bot.flows.processors.StepOutput
import org.ua.wohnung.bot.flows.processors.UserInputProcessingResult
import org.ua.wohnung.bot.flows.processors.UserInputProcessor
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.user.UserService

class PhoneNumberInputProcessor(private val userService: UserService): UserInputProcessor {
    override val supportedStep: FlowStep = FlowStep.PHONE_NUMBER

    override fun invoke(chatMetadata: ChatMetadata): UserInputProcessingResult {
        val clean = chatMetadata.input.filter { it.isDigit() }
        if (clean.length < 6 || clean.length > 32) {
            return UserInputProcessingResult.Error(
                message = "❌ Помилка, невірно введені дані. Будь-ласка, введіть свій номер телефона",
                finishSession = false
            )
        }
        userService.updateUserDetails(chatMetadata.userId) {
            phone = chatMetadata.input
        }
        return UserInputProcessingResult.Success
    }
}