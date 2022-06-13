package org.ua.wohnung.bot.flows.registereduser

import org.ua.wohnung.bot.flows.Flow
import org.ua.wohnung.bot.flows.FlowStep
import org.ua.wohnung.bot.flows.ReplyOption
import org.ua.wohnung.bot.flows.StepFactory
import org.ua.wohnung.bot.user.model.Role

class RegisteredUserFlow(private val stepFactory: StepFactory) : Flow() {

    override val supportedRole = Role.USER

    override fun initialize() {
        stepFactory.multipleButtons(
            id = FlowStep.REGISTERED_USER_CONVERSATION_START,
            ReplyOption("Доступне мені житло", FlowStep.REGISTERED_USER_LIST_APARTMENTS),
            ReplyOption("Видаліть мої дані", FlowStep.CONVERSATION_FINISH_REMOVAL)
        )
            .addSingle()
        stepFactory.multipleInlineButtons(
            id = FlowStep.REGISTERED_USER_LIST_APARTMENTS,
            ReplyOption("Відгукнутись на житло %s", FlowStep.REGISTERED_USER_APPLY_FOR_APARTMENT)
        ).addSingle()
        stepFactory.termination(FlowStep.REGISTERED_USER_REQUEST_RECEIVED).addSingle()
        stepFactory.termination(FlowStep.REGISTERED_USER_REQUEST_DECLINED).addSingle()
        stepFactory.termination(FlowStep.CONVERSATION_FINISH_REMOVAL).addSingle()
    }
}
