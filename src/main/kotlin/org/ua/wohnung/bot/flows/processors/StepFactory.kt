package org.ua.wohnung.bot.flows.processors

import org.ua.wohnung.bot.exception.ServiceException
import org.ua.wohnung.bot.flows.dto.ChatMetadata
import org.ua.wohnung.bot.flows.step.FlowStep

interface StepFactory: (ChatMetadata) -> StepOutput {
    val supportedStep: FlowStep
}

class StepFactoriesRegistry(vararg stepFactories: StepFactory) {
    private val internalMap: Map<FlowStep, StepFactory>

    init {
        internalMap = stepFactories.associateBy { it.supportedStep }
    }

    operator fun get(flowStep: FlowStep): StepFactory = internalMap[flowStep] ?:
        throw ServiceException.StepFactoryNotFound(flowStep)
}