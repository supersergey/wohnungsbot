package org.ua.wohnung.bot.flows

import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.user.model.Role

interface Flow {
    val supportedRole: Role
    val first: FlowStep
}

