package org.ua.wohnung.bot.flows.processors.userregistration.stepfactory

import org.ua.wohnung.bot.configuration.MessageSource
import org.ua.wohnung.bot.flows.dto.ChatMetadata
import org.ua.wohnung.bot.flows.processors.userregistration.userinputprocessor.Message
import org.ua.wohnung.bot.flows.processors.userregistration.userinputprocessor.StepOutput
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.user.UserService

class AcceptPoliciesStepFactory(userService: UserService, messageSource: MessageSource) :
    AbstractStepFactory(userService, messageSource) {

    override val supportedStep = FlowStep.ACCEPT_POLICIES

    override fun doInvoke(chatMetadata: ChatMetadata): StepOutput? {
        val message = messageSource[supportedStep]
        return StepOutput.InlineButtons(
            message = Message(message),
            nextStep = FlowStep.PERSONAL_DATA_PROCESSING_APPROVAL,
            replyOptions = listOf("Так", "Ні")
        )
//        return when (chatMetadata.input) {
//            "так" ->
//                StepOutput.InlineButtons(
//                    message = Message(message),
//                    nextStep = FlowStep.PERSONAL_DATA_PROCESSING_APPROVAL,
//                    replyOptions = listOf("Так", "Ні")
//                )
//            "ні" -> StepOutput.PlainText(
//                Message(messageSource[FlowStep.CONVERSATION_FINISHED_DECLINED]),
//                FlowStep.CONVERSATION_START
//            )
//            else -> null
//        }
    }
}
