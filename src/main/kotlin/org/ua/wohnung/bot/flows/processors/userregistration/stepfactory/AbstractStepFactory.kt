package org.ua.wohnung.bot.flows.processors.userregistration.stepfactory

import org.ua.wohnung.bot.configuration.MessageSource
import org.ua.wohnung.bot.exception.ServiceException
import org.ua.wohnung.bot.flows.dto.ChatMetadata
import org.ua.wohnung.bot.flows.processors.userregistration.userinputprocessor.Message
import org.ua.wohnung.bot.flows.processors.userregistration.userinputprocessor.StepOutput
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.user.UserService

abstract class AbstractStepFactory(
    protected val userService: UserService,
    protected val messageSource: MessageSource
) : (ChatMetadata) -> StepOutput {
    abstract val supportedStep: FlowStep

    abstract fun doInvoke(chatMetadata: ChatMetadata): StepOutput?

    override fun invoke(chatMetadata: ChatMetadata): StepOutput {
        val flow = userService.getFlowByUserId(chatMetadata.userId)
        return when (chatMetadata.input) {
            "/site" -> StepOutput.PlainText(
                message = Message(messageSource[FlowStep.FORWARD_TO_WEB]),
                nextStep = FlowStep.ACCEPT_POLICIES
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
                        message = Message("❌ Спочатку пройдіть реєстрацію. Для цього натисніть /start, а далі кнопку *Зарєеструватись*")
                    )
                }
            }
            "/conditions" -> StepOutput.PlainText(
                message = Message(messageSource[FlowStep.CONDITIONS]),
                nextStep = FlowStep.ACCEPT_POLICIES
            )
            "/start" -> StepOutput.InlineButtons(
                message = Message(messageSource[FlowStep.CONVERSATION_START]),
                nextStep = FlowStep.ACCEPT_POLICIES,
                replyOptions = listOf("Зареєструватись"),
                finishSession = true
            )

            else -> doInvoke(chatMetadata) ?: StepOutput.Error(
                message = Message("❌ Неправильно введені дані. Якшо бажаєте повернутись на початок, натисніть /start"),
                finishSession = false
            )
        }
    }
}

class StepFactoriesRegistry(vararg stepFactories: AbstractStepFactory) {
    private val internalMap: Map<FlowStep, AbstractStepFactory>

    init {
        internalMap = stepFactories.associateBy { it.supportedStep }
    }

    operator fun get(flowStep: FlowStep): AbstractStepFactory = internalMap[flowStep]
        ?: throw ServiceException.StepFactoryNotFound(flowStep)
}
