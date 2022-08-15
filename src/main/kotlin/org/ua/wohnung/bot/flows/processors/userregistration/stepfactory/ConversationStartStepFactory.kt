package org.ua.wohnung.bot.flows.processors.userregistration.stepfactory

import org.ua.wohnung.bot.configuration.MessageSource
import org.ua.wohnung.bot.flows.dto.ChatMetadata
import org.ua.wohnung.bot.flows.processors.userregistration.userinputprocessor.Message
import org.ua.wohnung.bot.flows.processors.userregistration.userinputprocessor.StepOutput
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.user.UserService

class ConversationStartStepFactory(
    userService: UserService,
    messageSource: MessageSource
) : AbstractStepFactory(userService, messageSource) {

    override val supportedStep = FlowStep.CONVERSATION_START

    override fun doInvoke(chatMetadata: ChatMetadata): StepOutput {
        return StepOutput.InlineButtons(
            message = Message(messageSource[supportedStep]),
            nextStep = FlowStep.ACCEPT_POLICIES,
            replyOptions = listOf("Так", "Ні")
        )
    }
}
