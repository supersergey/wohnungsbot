package org.ua.wohnung.bot.flows.processors.userregistration.userinputprocessor

import org.ua.wohnung.bot.flows.dto.ChatMetadata
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.user.UserService

class AllergiesInputProcessor(private val userService: UserService) : AbstractUserInputProcessor() {
    override val supportedStep: FlowStep = FlowStep.ALLERGIES

    override fun doInvoke(chatMetadata: ChatMetadata): UserInputProcessingResult {
        userService.updateUserDetails(chatMetadata.userId) {
            allergies = chatMetadata.input
        }
        return UserInputProcessingResult.Success
    }
}
