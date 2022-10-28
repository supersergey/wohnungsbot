package org.ua.wohnung.bot.flows.guestuser.processors

import org.ua.wohnung.bot.configuration.MessageSource
import org.ua.wohnung.bot.dto.ChatMetadata
import org.ua.wohnung.bot.flows.processors.StepOutput
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.flows.step.FlowStep.FOREIGN_LANGUAGES
import org.ua.wohnung.bot.flows.step.FlowStep.PETS
import org.ua.wohnung.bot.user.UserService

class PetsInputProcessor(userService: UserService, messageSource: MessageSource) :
    AbstractGuestUserInputProcessor(userService, messageSource) {
    override val supportedStep: FlowStep = PETS

    override fun processSpecificCommands(chatMetadata: ChatMetadata): StepOutput? {
        if (chatMetadata.input !in setOf("так", "ні")) {
            return null
        }
        userService.updateUserDetails(chatMetadata.userId) {
            pets = chatMetadata.input == "так"
        }
        return StepOutput.PlainText(
            message = messageSource[FOREIGN_LANGUAGES],
            nextStep = FlowStep.FOREIGN_LANGUAGES
        )
    }
}
