package org.ua.wohnung.bot.flows

import org.ua.wohnung.bot.user.model.Role

abstract class Flow {
    private val internalMap = mutableMapOf<FlowStep, Step>()

    private var first: Step? = null

    abstract val supportedRole: Role

    open fun first(username: String = "") = requireNotNull(first)

    fun current(flowStep: FlowStep): Step = internalMap[flowStep] ?: first()

    fun next(currentStep: FlowStep, userInput: String): Step? {
        return internalMap[currentStep]?.let {
            val next = if (it.reply is Reply.Inline) {
                it.reply.options[userInput]
            } else {
                it.reply.options[Reply.ANY_ANSWER_ACCEPTED]
            }
            internalMap[next]
        }
    }

    private fun add(step: Step) {
        if (internalMap.isEmpty()) {
            first = step
        }
        internalMap[step.id] = step
    }

    abstract fun initialize()

    protected fun <T : Step> T.addSingle() {
        add(this)
    }

    protected fun List<String>.allTo(next: FlowStep) = map { it to next }.toTypedArray()
}
