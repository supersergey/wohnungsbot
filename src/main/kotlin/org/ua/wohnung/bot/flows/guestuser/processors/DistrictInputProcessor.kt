package org.ua.wohnung.bot.flows.guestuser.processors

import org.ua.wohnung.bot.configuration.MessageSource
import org.ua.wohnung.bot.dto.ChatMetadata
import org.ua.wohnung.bot.flows.processors.StepOutput
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.flows.step.FlowStep.DISTRICT_SELECTION
import org.ua.wohnung.bot.flows.step.FlowStep.FAMILY_COUNT
import org.ua.wohnung.bot.user.UserService

class DistrictInputProcessor(userService: UserService, messageSource: MessageSource) :
    AbstractGuestUserInputProcessor(userService, messageSource) {
    override val supportedStep: FlowStep = DISTRICT_SELECTION

    override fun processSpecificCommands(chatMetadata: ChatMetadata): StepOutput? {
        if (chatMetadata.input.filter { it.isLetter() }.length < 4) {
            return null
        }
        userService.updateUserDetails(chatMetadata.userId) {
            district = chatMetadata.input
        }
        return StepOutput.InlineButtons(
            message = messageSource[FAMILY_COUNT],
            nextStep = FAMILY_COUNT,
            replyOptions = (1..12).map { "$it" }
        )
    }
}
