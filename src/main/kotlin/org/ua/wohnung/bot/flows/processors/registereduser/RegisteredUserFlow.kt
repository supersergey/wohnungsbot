package org.ua.wohnung.bot.flows.processors.registereduser

import org.ua.wohnung.bot.flows.Flow
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.user.model.Role

class RegisteredUserFlow : Flow {

    override val supportedRole = Role.USER

    override val first: FlowStep = FlowStep.REGISTERED_USER_CONVERSATION_START
}
