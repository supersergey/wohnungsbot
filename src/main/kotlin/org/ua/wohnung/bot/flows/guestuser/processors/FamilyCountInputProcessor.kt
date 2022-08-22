package org.ua.wohnung.bot.flows.guestuser.processors

import org.ua.wohnung.bot.configuration.MessageSource
import org.ua.wohnung.bot.flows.dto.ChatMetadata
import org.ua.wohnung.bot.flows.processors.Message
import org.ua.wohnung.bot.flows.processors.StepOutput
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.flows.step.FlowStep.*
import org.ua.wohnung.bot.user.UserService

class FamilyCountInputProcessor(userService: UserService, messageSource: MessageSource) :
    GuestUserInputProcessor(userService, messageSource) {
    override val supportedStep: FlowStep = FAMILY_COUNT

    override fun processSpecificCommands(chatMetadata: ChatMetadata): StepOutput? {
        if (chatMetadata.input !in (1..12).map { "$it" }) {
            return null
        }
        userService.updateUserDetails(chatMetadata.userId) {
            numberOfTenants = chatMetadata.input.toShort()
        }
        return StepOutput.PlainText(
            message = Message(messageSource[FAMILY_MEMBERS]),
            nextStep = FAMILY_MEMBERS
        )
    }
}
