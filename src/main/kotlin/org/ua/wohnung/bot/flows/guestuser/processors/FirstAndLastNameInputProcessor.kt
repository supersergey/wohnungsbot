package org.ua.wohnung.bot.flows.guestuser.processors

import org.ua.wohnung.bot.configuration.MessageSource
import org.ua.wohnung.bot.flows.dto.ChatMetadata
import org.ua.wohnung.bot.flows.processors.Message
import org.ua.wohnung.bot.flows.processors.StepOutput
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.flows.step.FlowStep.*
import org.ua.wohnung.bot.user.UserService

class FirstAndLastNameInputProcessor(userService: UserService, messageSource: MessageSource) :
    GuestUserInputProcessor(userService, messageSource) {
    override val supportedStep: FlowStep = FIRSTNAME_AND_LASTNAME

    override fun processSpecificCommands(chatMetadata: ChatMetadata): StepOutput? {
        if (chatMetadata.input.length !in (4..256)) {
            return null
        }
        userService.updateUserDetails(chatMetadata.userId) {
            firstLastName = chatMetadata.input.split("\\s")
                .onEach { word -> word.replaceFirstChar { it.uppercase() }}
                .joinToString(" ")
        }
        return StepOutput.PlainText(
            message = Message(messageSource[PHONE_NUMBER]),
            nextStep = PHONE_NUMBER
        )
    }
}
