package org.ua.wohnung.bot.flows.guestuser.processors

import org.ua.wohnung.bot.configuration.MessageSource
import org.ua.wohnung.bot.flows.dto.ChatMetadata
import org.ua.wohnung.bot.flows.processors.Message
import org.ua.wohnung.bot.flows.processors.StepOutput
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.flows.step.FlowStep.*
import org.ua.wohnung.bot.user.UserService

class DistrictInputProcessor(userService: UserService, messageSource: MessageSource) :
    GuestUserInputProcessor(userService, messageSource) {
    override val supportedStep: FlowStep = DISTRICT_SELECTION

    override fun processSpecificCommands(chatMetadata: ChatMetadata): StepOutput? {
        if (chatMetadata.input.filter { it.isLetter() }.length < 4) {
            return null
        }
        userService.updateUserDetails(chatMetadata.userId) {
            district = chatMetadata.input
        }
        return StepOutput.InlineButtons(
            message = Message(messageSource[FAMILY_COUNT]),
            nextStep = FAMILY_COUNT,
            replyOptions = (1..12).map { "$it" }
        )
    }
}
