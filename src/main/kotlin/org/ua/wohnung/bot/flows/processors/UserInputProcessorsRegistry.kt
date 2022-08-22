package org.ua.wohnung.bot.flows.processors

import org.ua.wohnung.bot.flows.AbstractUserInputProcessor
import org.ua.wohnung.bot.flows.step.FlowStep

class UserInputProcessorsRegistry(vararg processors: AbstractUserInputProcessor) {
    private val map: Map<FlowStep, AbstractUserInputProcessor>

    init {
        map = processors.associateBy { it.supportedStep }
    }

    operator fun get(step: FlowStep): AbstractUserInputProcessor? = map[step]
}
