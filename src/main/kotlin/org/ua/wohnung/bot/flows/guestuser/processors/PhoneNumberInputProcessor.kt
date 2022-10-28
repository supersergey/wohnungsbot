package org.ua.wohnung.bot.flows.guestuser.processors

import org.ua.wohnung.bot.configuration.MessageSource
import org.ua.wohnung.bot.flows.dto.ChatMetadata
import org.ua.wohnung.bot.flows.processors.StepOutput
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.flows.step.FlowStep.PHONE_NUMBER
import org.ua.wohnung.bot.user.UserService

class PhoneNumberInputProcessor(userService: UserService, messageSource: MessageSource) :
    GuestUserInputProcessor(userService, messageSource) {
    override val supportedStep: FlowStep = PHONE_NUMBER

    override fun processSpecificCommands(chatMetadata: ChatMetadata): StepOutput? {
        val clean = chatMetadata.input.filter { it.isDigit() }
        if (clean.length < 6 || clean.length > 32) {
            return null
        }
        userService.updateUserDetails(chatMetadata.userId) {
            phone = chatMetadata.input
        }
        return StepOutput.PlainText(
            message = messageSource[FlowStep.EMAIL],
            nextStep = FlowStep.EMAIL
        )
    }
}
