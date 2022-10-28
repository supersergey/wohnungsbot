package org.ua.wohnung.bot.flows.guestuser.processors

import org.ua.wohnung.bot.configuration.MessageSource
import org.ua.wohnung.bot.dto.ChatMetadata
import org.ua.wohnung.bot.flows.processors.StepOutput
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.flows.step.FlowStep.EMAIL
import org.ua.wohnung.bot.user.UserService

class EmailInputProcessor(userService: UserService, messageSource: MessageSource) :
    GuestUserInputProcessor(userService, messageSource) {
    override val supportedStep: FlowStep = EMAIL
    private val emailRegexp = ".+@.+\\..{2,5}".toRegex()

    override fun processSpecificCommands(chatMetadata: ChatMetadata): StepOutput? {
        if (!chatMetadata.input.matches(emailRegexp)) {
            return null
        }
        userService.updateUserDetails(chatMetadata.userId) {
            email = chatMetadata.input
        }
        return StepOutput.InlineButtons(
            message = messageSource[FlowStep.PETS],
            nextStep = FlowStep.PETS,
            replyOptions = listOf("Так", "Ні")
        )
    }
}
