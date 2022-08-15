package org.ua.wohnung.bot.flows.processors.userregistration.userinputprocessor

import org.ua.wohnung.bot.flows.dto.ChatMetadata
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.user.UserService

class FirstAndLastNameInputProcessor(private val userService: UserService) : AbstractUserInputProcessor() {
    override val supportedStep: FlowStep = FlowStep.FIRSTNAME_AND_LASTNAME

    override fun doInvoke(chatMetadata: ChatMetadata): UserInputProcessingResult? {
        if (chatMetadata.input.length !in (4..256)) {
            return null
        }
        userService.updateUserDetails(chatMetadata.userId) {
            firstLastName = chatMetadata.input
        }
        return UserInputProcessingResult.Success
    }
}
