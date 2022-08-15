package org.ua.wohnung.bot.flows.processors.userregistration.userinputprocessor

import org.ua.wohnung.bot.flows.dto.ChatMetadata
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.user.UserService

class ForeignLanguagesInputProcessor(private val userService: UserService) : AbstractUserInputProcessor() {
    override val supportedStep: FlowStep = FlowStep.FOREIGN_LANGUAGES

    override fun doInvoke(chatMetadata: ChatMetadata): UserInputProcessingResult? {
        if (chatMetadata.input.filter { it.isLetter() }.length < 2) {
            return null
        }
        userService.updateUserDetails(chatMetadata.userId) {
            foreignLanguages = chatMetadata.input
        }
        return UserInputProcessingResult.Success
    }
}
