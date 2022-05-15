package org.ua.wohnung.bot

interface Flow {
    fun first(): Step
    fun next(stepId: String, userInput: String): Step?
    fun add(step: Step)
}

class UserRegistrationFlow(private val firstStep: Step) : Flow {

    private val internalMap = mutableMapOf<String, Step>()

    init {
        internalMap[firstStep.caption] = firstStep
    }

    override fun first(): Step = firstStep

    override fun next(stepId: String, userInput: String): Step? {
        return internalMap[stepId]?.let {
            it.reply.options[userInput]
        }
    }

    override fun add(step: Step) {
        internalMap[step.caption] = step
    }
}
