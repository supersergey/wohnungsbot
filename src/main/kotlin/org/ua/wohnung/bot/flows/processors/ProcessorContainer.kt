package org.ua.wohnung.bot.flows.processors

import org.ua.wohnung.bot.flows.FlowStep

sealed class ProcessorContainer<T : Processor>(vararg processors: T) {
    protected val map: Map<FlowStep, T>

    init {
        map = processors.associateBy { it.stepId }
    }

    class PreProcessors(vararg preProcessors: PreProcessor) : ProcessorContainer<PreProcessor>(*preProcessors) {
        operator fun get(stepId: FlowStep): PreProcessor = map[stepId] ?: PreProcessor.Empty
    }
    class PostProcessors(vararg postProcessors: PostProcessor) : ProcessorContainer<PostProcessor>(*postProcessors) {
        operator fun get(stepId: FlowStep): PostProcessor = map[stepId] ?: PostProcessor.Empty
    }
    class MessagePreProcessors(vararg messagePreProcessors: MessagePreProcessor): ProcessorContainer<MessagePreProcessor>(*messagePreProcessors) {
        operator fun get(stepId: FlowStep): MessagePreProcessor = map[stepId] ?: MessagePreProcessor.Empty
    }
}