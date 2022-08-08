package org.ua.wohnung.bot.flows.processors.userregistration.stepfactory

import org.ua.wohnung.bot.configuration.MessageSource
import org.ua.wohnung.bot.flows.dto.ChatMetadata
import org.ua.wohnung.bot.flows.processors.Message
import org.ua.wohnung.bot.flows.processors.StepFactory
import org.ua.wohnung.bot.flows.processors.StepOutput
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.user.UserService

class ConversationStartStepFactory(
    private val messageSource: MessageSource,
    private val userService: UserService
    ) : StepFactory {

        override val supportedStep = FlowStep.CONVERSATION_START

        override fun invoke(chatMetadata: ChatMetadata): StepOutput {
            return when (chatMetadata.input) {
                "/site" -> StepOutput.PlainText(
                    message = Message(messageSource[FlowStep.FORWARD_TO_WEB]),
                    nextStep = FlowStep.CONVERSATION_START
                )
                "/list_apartments" -> {
                    if (userService.isRegistrationComplete(chatMetadata.userId)) {
                        StepOutput.MarkupButtons(
                            message = Message("list of apartments"),
                            replyOptions = listOf("ap1", "ap2", "ap3"),
                            nextStep = FlowStep.REGISTERED_USER_LIST_APARTMENTS
                        )
                    } else {
                        StepOutput.Error(
                            message = Message("Спочатку пройдіть реєстрацію. Для цього натисніть /start, а далі кнопку *Зарєеструватись*")
                        )
                    }
                }
                "зареєструватися" -> {
                    if (chatMetadata.username.isNullOrBlank()) {
                        StepOutput.PlainText(
                            message = Message(messageSource[FlowStep.NO_USER_NAME_ERROR]),
                            nextStep = FlowStep.CONVERSATION_START
                        )
                    } else {
                        StepOutput.InlineButtons(
                            message = Message(messageSource[FlowStep.ACCEPT_POLICIES]),
                            nextStep = FlowStep.PERSONAL_DATA_PROCESSING_APPROVAL,
                            replyOptions = listOf("Так", "Ні"),
                            isEditMessage = false
                        )
                    }
                }
                else -> StepOutput.MarkupButtons(
                    message = Message(messageSource[FlowStep.CONVERSATION_START]),
                    nextStep = FlowStep.CONVERSATION_START,
                    replyOptions = listOf("Зареєструватися")
                )
            }
        }
    }