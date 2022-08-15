package org.ua.wohnung.bot.flows

import org.ua.wohnung.bot.user.model.Role

class FlowRegistry(vararg flows: Flow) {

    private val flowMap: Map<Role, Flow>

    init {
        flowMap = flows.associateBy { it.supportedRole }
    }

    operator fun get(role: Role): Flow = requireNotNull(flowMap[role])
}
