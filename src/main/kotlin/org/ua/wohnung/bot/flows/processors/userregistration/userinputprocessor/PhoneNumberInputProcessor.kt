package org.ua.wohnung.bot.flows.processors.userregistration.userinputprocessor

import org.ua.wohnung.bot.flows.dto.ChatMetadata
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.user.UserService

class PhoneNumberInputProcessor(private val userService: UserService) : AbstractUserInputProcessor() {
    override val supportedStep: FlowStep = FlowStep.PHONE_NUMBER

    override fun doInvoke(chatMetadata: ChatMetadata): UserInputProcessingResult? {
        val clean = chatMetadata.input.filter { it.isDigit() }
        if (clean.length < 6 || clean.length > 32) {
            return null
        }
        userService.updateUserDetails(chatMetadata.userId) {
            phone = chatMetadata.input
        }
        return UserInputProcessingResult.Success
    }
}
