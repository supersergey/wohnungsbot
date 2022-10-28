package org.ua.wohnung.bot.flows.guestuser.processors

import org.ua.wohnung.bot.configuration.MessageSource
import org.ua.wohnung.bot.dto.ChatMetadata
import org.ua.wohnung.bot.flows.processors.StepOutput
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.flows.step.FlowStep.FAMILY_COUNT
import org.ua.wohnung.bot.flows.step.FlowStep.FAMILY_MEMBERS
import org.ua.wohnung.bot.user.UserService

class FamilyCountInputProcessor(userService: UserService, messageSource: MessageSource) :
    AbstractGuestUserInputProcessor(userService, messageSource) {
    override val supportedStep: FlowStep = FAMILY_COUNT

    override fun processSpecificCommands(chatMetadata: ChatMetadata): StepOutput? {
        if (chatMetadata.input !in (1..12).map { "$it" }) {
            return null
        }
        userService.updateUserDetails(chatMetadata.userId) {
            numberOfTenants = chatMetadata.input.toShort()
        }
        return StepOutput.PlainText(
            message = messageSource[FAMILY_MEMBERS],
            nextStep = FAMILY_MEMBERS
        )
    }
}
