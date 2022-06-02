package org.ua.wohnung.bot.flows.processors

import org.ua.wohnung.bot.flows.FlowStep

interface Processor {
    operator fun invoke(username: String, input: String)
    val stepId: FlowStep

    object Empty : Processor {
        override val stepId = FlowStep.CONVERSATION_START
        override operator fun invoke(username: String, input: String) {}
    }
}

interface PreProcessor : Processor {
    object Empty: PreProcessor {
        override val stepId = FlowStep.CONVERSATION_START
        override fun invoke(username: String, input: String) {}
    }
}
interface PostProcessor : Processor {
    object Empty: PostProcessor {
        override val stepId = FlowStep.CONVERSATION_START
        override fun invoke(username: String, input: String) {}
    }
}

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
}