package org.ua.wohnung.bot.flows.processors.userregistration.stepfactory

import org.ua.wohnung.bot.configuration.MessageSource
import org.ua.wohnung.bot.flows.dto.ChatMetadata
import org.ua.wohnung.bot.flows.processors.userregistration.userinputprocessor.Message
import org.ua.wohnung.bot.flows.processors.userregistration.userinputprocessor.StepOutput
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.flows.step.UserRegistrationFlow
import org.ua.wohnung.bot.user.UserService

class DummyInitStepFactory(
    userService: UserService,
    messageSource: MessageSource
) : AbstractStepFactory(userService, messageSource) {
    override val supportedStep: FlowStep = FlowStep.INITIAL

    override fun doInvoke(chatMetadata: ChatMetadata): StepOutput {
        return when (userService.getFlowByUserId(chatMetadata.userId)) {
            is UserRegistrationFlow -> StepOutput.InlineButtons(
                message = Message(messageSource[FlowStep.CONVERSATION_START]),
                nextStep = FlowStep.ACCEPT_POLICIES,
                replyOptions = listOf("Зарєеструватись")
            )
            else -> throw Exception("Unexpected error")
        }
    }
}
