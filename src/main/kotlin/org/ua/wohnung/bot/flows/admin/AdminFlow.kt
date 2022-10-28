package org.ua.wohnung.bot.flows.admin

import org.ua.wohnung.bot.flows.Flow
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.user.model.Role

class AdminFlow : Flow {
    override val supportedRole: Role = Role.ADMIN
    override val first: FlowStep = FlowStep.ADMIN_START
}
