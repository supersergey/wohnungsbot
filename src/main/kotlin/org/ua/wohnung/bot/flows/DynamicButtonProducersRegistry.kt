package org.ua.wohnung.bot.flows

import org.ua.wohnung.bot.flows.step.FlowStep

class DynamicButtonProducersRegistry(vararg dynamicButtonsProducers: DynamicButtonsProducer) {
    private val map: Map<FlowStep, DynamicButtonsProducer>

    init {
        map = dynamicButtonsProducers.associateBy { it.supportedStep }
    }

    operator fun get(id: FlowStep): DynamicButtonsProducer? = map[id]
}