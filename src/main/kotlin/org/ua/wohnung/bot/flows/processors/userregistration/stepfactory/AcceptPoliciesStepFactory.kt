package org.ua.wohnung.bot.flows.processors.userregistration.stepfactory

import org.ua.wohnung.bot.configuration.MessageSource
import org.ua.wohnung.bot.flows.dto.ChatMetadata
import org.ua.wohnung.bot.flows.processors.Message
import org.ua.wohnung.bot.flows.processors.StepFactory
import org.ua.wohnung.bot.flows.processors.StepOutput
import org.ua.wohnung.bot.flows.step.FlowStep

class AcceptPoliciesStepFactory(private val messageSource: MessageSource) : StepFactory {

        override val supportedStep = FlowStep.ACCEPT_POLICIES

        override fun invoke(chatMetadata: ChatMetadata): StepOutput {
            val message = messageSource[supportedStep]
            return when (chatMetadata.input) {
                "так" ->
                    StepOutput.InlineButtons(
                        message = Message(message),
                        nextStep = FlowStep.PERSONAL_DATA_PROCESSING_APPROVAL,
                        replyOptions = listOf("Так", "Ні")
                    )
                "ні" -> StepOutput.PlainText(
                    Message(messageSource[FlowStep.CONVERSATION_FINISHED_DECLINED]),
                    FlowStep.CONVERSATION_START
                )
                "/start" -> TODO()
                "/site" -> TODO()
                "/conditions" -> TODO()
                "/list_apartments" -> TODO()
                else -> TODO()
            }
        }
    }