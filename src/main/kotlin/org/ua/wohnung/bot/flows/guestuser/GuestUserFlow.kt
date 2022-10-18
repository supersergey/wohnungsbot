package org.ua.wohnung.bot.flows.guestuser

import org.ua.wohnung.bot.flows.Flow
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.user.model.Role

class GuestUserFlow : Flow {
    override val supportedRole = Role.GUEST
    override val first = FlowStep.CONVERSATION_START
}
