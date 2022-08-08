package org.ua.wohnung.bot.flows.processors

import org.ua.wohnung.bot.flows.step.FlowStep

class UserInputProcessorsRegistry(vararg processors: UserInputProcessor) {
    private val map: Map<FlowStep, UserInputProcessor>

    init {
        map = processors.associateBy { it.supportedStep }
    }

    operator fun get(step: FlowStep): UserInputProcessor? = map[step]
}
