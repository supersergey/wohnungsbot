package org.ua.wohnung.bot.flows.processors.userregistration.userinputprocessor

import org.ua.wohnung.bot.flows.dto.ChatMetadata
import org.ua.wohnung.bot.flows.processors.UserInputProcessingResult
import org.ua.wohnung.bot.flows.processors.UserInputProcessor
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.user.UserService

class DistrictInputProcessor(private val userService: UserService): UserInputProcessor {
    override val supportedStep: FlowStep = FlowStep.DISTRICT_SELECTION

    override fun invoke(chatMetadata: ChatMetadata): UserInputProcessingResult {
        userService.updateUserDetails(chatMetadata.userId) {
            district = chatMetadata.input
        }
        return UserInputProcessingResult.Success
    }
}