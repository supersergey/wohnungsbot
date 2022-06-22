package org.ua.wohnung.bot.flows.registereduser

import org.ua.wohnung.bot.flows.DynamicButtonsProducer
import org.ua.wohnung.bot.flows.Flow
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.flows.step.ReplyOption
import org.ua.wohnung.bot.flows.step.StepFactory
import org.ua.wohnung.bot.user.model.Role

class RegisteredUserFlow(
    private val stepFactory: StepFactory
) : Flow() {

    override val supportedRole = Role.USER

    override fun initialize() {
        stepFactory.multipleButtons(
            id = FlowStep.REGISTERED_USER_CONVERSATION_START,
            ReplyOption("Доступне мені житло", FlowStep.REGISTERED_USER_LIST_APARTMENTS),
            ReplyOption("Видаліть мої дані", FlowStep.CONVERSATION_FINISH_REMOVAL)
        )
            .addSingle()
        stepFactory.multipleDynamicButtons(
            id = FlowStep.REGISTERED_USER_LIST_APARTMENTS,
            next = FlowStep.REGISTERED_USER_REQUEST_RECEIVED
        ).addSingle()
        stepFactory.termination(FlowStep.REGISTERED_USER_REQUEST_RECEIVED).addSingle()
        stepFactory.termination(FlowStep.REGISTERED_USER_REQUEST_DECLINED).addSingle()
        stepFactory.termination(FlowStep.CONVERSATION_FINISH_REMOVAL).addSingle()
    }
}
