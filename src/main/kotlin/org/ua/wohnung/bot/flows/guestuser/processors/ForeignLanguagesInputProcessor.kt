package org.ua.wohnung.bot.flows.guestuser.processors

import org.ua.wohnung.bot.configuration.MessageSource
import org.ua.wohnung.bot.flows.dto.ChatMetadata
import org.ua.wohnung.bot.flows.processors.StepOutput
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.flows.step.FlowStep.FOREIGN_LANGUAGES
import org.ua.wohnung.bot.flows.step.FlowStep.READY_TO_MOVE
import org.ua.wohnung.bot.user.UserService

class ForeignLanguagesInputProcessor(userService: UserService, messageSource: MessageSource) :
    GuestUserInputProcessor(userService, messageSource) {
    override val supportedStep: FlowStep = FOREIGN_LANGUAGES

    override fun processSpecificCommands(chatMetadata: ChatMetadata): StepOutput? {
        if (chatMetadata.input.filter { it.isLetter() }.length < 2) {
            return null
        }
        userService.updateUserDetails(chatMetadata.userId) {
            foreignLanguages = chatMetadata.input
        }
        return StepOutput.InlineButtons(
            message = messageSource[READY_TO_MOVE],
            nextStep = READY_TO_MOVE,
            replyOptions = listOf("Так", "Ні")
        )
    }
}
