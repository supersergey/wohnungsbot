package org.ua.wohnung.bot.flows.step

import org.ua.wohnung.bot.configuration.MessageSource
import org.ua.wohnung.bot.flows.processors.ProcessorContainer

class StepFactory(
    private val messageSource: MessageSource,
    private val preProcessors: ProcessorContainer.PreProcessors,
    private val postProcessors: ProcessorContainer.PostProcessors
) {
    fun singleReply(
        id: FlowStep,
        next: FlowStep
    ): Step.General =
        Step.General(id, messageSource[id], Reply.SingleText(next), preProcessors[id], postProcessors[id])

    fun multipleButtons(
        id: FlowStep,
        vararg replies: ReplyOption
    ): Step.General =
        Step.General(id, messageSource[id], Reply.WithButtons(*replies), preProcessors[id], postProcessors[id])

    fun multipleInlineButtons(
        id: FlowStep,
        vararg replies: ReplyOption
    ): Step.General =
        Step.General(id, messageSource[id], Reply.WithInlineButtons(*replies), preProcessors[id], postProcessors[id])

    fun multipleDynamicButtons(
        id: FlowStep,
        next: FlowStep
    ): Step.General = Step.General(
        id,
        messageSource[id],
        Reply.WithDynamicButtons(next),
        preProcessors[id],
        postProcessors[id]
    )

    fun multipleTextOptions(
        id: FlowStep,
        vararg replies: ReplyOption
    ): Step.General =
        Step.General(id, messageSource[id], Reply.MultiText(*replies), preProcessors[id], postProcessors[id])

    fun termination(
        id: FlowStep,
    ): Step.Termination =
        Step.Termination(id, messageSource[id], preProcessors[id], postProcessors[id])
}
