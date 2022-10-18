package org.ua.wohnung.bot.flows.guestuser.processors

import org.ua.wohnung.bot.configuration.MessageSource
import org.ua.wohnung.bot.flows.dto.ChatMetadata
import org.ua.wohnung.bot.flows.processors.StepOutput
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.flows.step.FlowStep.ALLERGIES
import org.ua.wohnung.bot.flows.step.FlowStep.GUEST_USER_REGISTRATION_FINISHED_SUCCESS
import org.ua.wohnung.bot.user.UserService

class AllergiesInputProcessor(userService: UserService, messageSource: MessageSource) :
    GuestUserInputProcessor(userService, messageSource) {
    override val supportedStep: FlowStep = ALLERGIES

    override fun processSpecificCommands(chatMetadata: ChatMetadata): StepOutput? {
        if (chatMetadata.input.length > 1024) {
            return null
        }
        userService.updateUserDetails(chatMetadata.userId) {
            allergies = chatMetadata.input
        }
        return StepOutput.PlainText(
            message = messageSource[GUEST_USER_REGISTRATION_FINISHED_SUCCESS],
            finishSession = true
        )
    }
}
