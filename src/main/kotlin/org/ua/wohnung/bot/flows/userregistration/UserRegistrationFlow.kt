package org.ua.wohnung.bot.flows.userregistration

import org.ua.wohnung.bot.flows.Reply
import org.ua.wohnung.bot.flows.Step

interface Flow {
    fun first(): Step
    fun current(flowStep: FlowStep): Step?
    fun next(currentStep: FlowStep, userInput: String): Step?
    fun add(step: Step)
}

class UserRegistrationFlow : Flow {

    private val internalMap = mutableMapOf<FlowStep, Step>()
    private var firstStep: Step? = null

    override fun first(): Step = requireNotNull(firstStep)

    override fun current(flowStep: FlowStep): Step? = internalMap[flowStep] ?: firstStep

    override fun add(step: Step) {
        if (internalMap.isEmpty()) {
            firstStep = step
        }
        internalMap[step.id] = step
    }

    override fun next(currentStep: FlowStep, userInput: String): Step? {
        return internalMap[currentStep]?.let {
            val next = if (it.reply is Reply.Inline) {
                it.reply.options[userInput]
            } else {
                it.reply.options[Reply.ANY_ANSWER_ACCEPTED]
            }
            internalMap[next]
        }
    }
}
