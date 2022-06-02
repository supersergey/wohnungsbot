package org.ua.wohnung.bot.flows.registereduser

import org.ua.wohnung.bot.flows.Flow
import org.ua.wohnung.bot.flows.FlowStep
import org.ua.wohnung.bot.flows.StepFactory
import org.ua.wohnung.bot.user.model.Role

class RegisteredUserFlow(private val stepFactory: StepFactory) : Flow() {

    override val supportedRole = Role.USER

    override fun initialize() {
        stepFactory.multipleReplies(
            id = FlowStep.REGISTERED_USER_CONVERSATION_START,
            "Доступне мені житло" to FlowStep.REGISTERED_USER_LIST_APARTMENTS,
            "Видаліть мої дані" to FlowStep.CONVERSATION_FINISH_REMOVAL
        )
            .addSingle()
        stepFactory.termination(FlowStep.REGISTERED_USER_LIST_APARTMENTS).addSingle()
        stepFactory.termination(FlowStep.CONVERSATION_FINISH_REMOVAL).addSingle()
    }
}