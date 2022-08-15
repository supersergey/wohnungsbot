package org.ua.wohnung.bot.flows.processors.userregistration.userinputprocessor

import org.ua.wohnung.bot.flows.dto.ChatMetadata
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.user.UserService

class PetsInputProcessor(private val userService: UserService) : AbstractUserInputProcessor() {
    override val supportedStep: FlowStep = FlowStep.PETS

    override fun doInvoke(chatMetadata: ChatMetadata): UserInputProcessingResult? {
        if (chatMetadata.input !in setOf("так", "ні")) {
            return null
        }
        userService.updateUserDetails(chatMetadata.userId) {
            pets = chatMetadata.input == "так"
        }
        return UserInputProcessingResult.Success
    }
}
