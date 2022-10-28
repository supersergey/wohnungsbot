package org.ua.wohnung.bot.flows.guestuser.processors

import org.ua.wohnung.bot.configuration.MessageSource
import org.ua.wohnung.bot.dto.ChatMetadata
import org.ua.wohnung.bot.flows.processors.StepOutput
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.flows.step.FlowStep.FIRSTNAME_AND_LASTNAME
import org.ua.wohnung.bot.flows.step.FlowStep.WBS
import org.ua.wohnung.bot.user.UserService

class FirstAndLastNameInputProcessor(userService: UserService, messageSource: MessageSource) :
    AbstractGuestUserInputProcessor(userService, messageSource) {
    override val supportedStep: FlowStep = FIRSTNAME_AND_LASTNAME

    override fun processSpecificCommands(chatMetadata: ChatMetadata): StepOutput? {
        if (chatMetadata.input.length !in (4..256)) {
            return null
        }
        userService.updateUserDetails(chatMetadata.userId) {
            firstLastName = chatMetadata.input.split("\\s")
                .onEach { word -> word.replaceFirstChar { it.uppercase() } }
                .joinToString(" ")
        }

        return StepOutput.InlineButtons(
            message = messageSource[WBS],
            nextStep = WBS,
            replyOptions = listOf("Так", "Ні")
        )
    }
}
