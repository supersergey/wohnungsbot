package org.ua.wohnung.bot.flows.processors.userregistration.stepfactory

import org.ua.wohnung.bot.configuration.MessageSource
import org.ua.wohnung.bot.flows.dto.ChatMetadata
import org.ua.wohnung.bot.flows.processors.userregistration.userinputprocessor.Message
import org.ua.wohnung.bot.flows.processors.userregistration.userinputprocessor.StepOutput
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.user.UserService

class ReadyToMoveStepFactory(
    userService: UserService,
    messageSource: MessageSource
) : AbstractStepFactory(userService, messageSource) {
    override val supportedStep: FlowStep = FlowStep.READY_TO_MOVE

    override fun doInvoke(chatMetadata: ChatMetadata): StepOutput {
        return StepOutput.InlineButtons(
            message = Message(messageSource[supportedStep]),
            nextStep = FlowStep.ALLERGIES,
            replyOptions = listOf("Так", "Ні")
        )
    }
}
