package org.ua.wohnung.bot.flows.guestuser.processors

import org.ua.wohnung.bot.configuration.MessageSource
import org.ua.wohnung.bot.dto.ChatMetadata
import org.ua.wohnung.bot.flows.processors.StepOutput
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.flows.step.FlowStep.ALLERGIES
import org.ua.wohnung.bot.flows.step.FlowStep.READY_TO_MOVE
import org.ua.wohnung.bot.user.UserService

class ReadyToMoveInputProcessor(userService: UserService, messageSource: MessageSource) :
    AbstractGuestUserInputProcessor(userService, messageSource) {
    override val supportedStep: FlowStep = READY_TO_MOVE

    override fun processSpecificCommands(chatMetadata: ChatMetadata): StepOutput {
        if (chatMetadata.input !in setOf("так", "ні")) {
            return StepOutput.Error(
                message = "❌ Неправильно введені дані. Скористайтесь кнопками вибору. Якшо бажаєте повернутись на початок, натисніть /start",
                finishSession = false
            )
        }
        userService.updateUserDetails(chatMetadata.userId) {
            readyToMove = chatMetadata.input == "так"
        }
        return StepOutput.PlainText(
            message = messageSource[ALLERGIES],
            nextStep = ALLERGIES
        )
    }
}
