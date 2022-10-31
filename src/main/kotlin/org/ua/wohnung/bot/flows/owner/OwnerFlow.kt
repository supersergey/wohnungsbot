package org.ua.wohnung.bot.flows.owner

import org.ua.wohnung.bot.flows.Flow
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.user.model.Role

class OwnerFlow : Flow {
    override val supportedRole = Role.OWNER
    override val first: FlowStep = FlowStep.OWNER_START
}
