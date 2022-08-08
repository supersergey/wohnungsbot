package org.ua.wohnung.bot.flows.processors.userregistration.userinputprocessor

import org.ua.wohnung.bot.flows.dto.ChatMetadata
import org.ua.wohnung.bot.flows.processors.UserInputProcessingResult
import org.ua.wohnung.bot.flows.processors.UserInputProcessor
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.user.UserService

class AllergiesInputProcessor(private val userService: UserService): UserInputProcessor {
    override val supportedStep: FlowStep = FlowStep.ALLERGIES

    override fun invoke(chatMetadata: ChatMetadata): UserInputProcessingResult {
        userService.updateUserDetails(chatMetadata.userId) {
            allergies = chatMetadata.input
        }
        return UserInputProcessingResult.Success
    }
}