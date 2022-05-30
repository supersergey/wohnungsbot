package org.ua.wohnung.bot.flows

interface Flow {
    fun first(): Step
    fun next(stepId: String, userInput: String): Step?
    fun add(step: Step)
}

class UserRegistrationFlow : Flow {

    private val internalMap = mutableMapOf<String, Step>()
    private var firstStep: Step? = null

    override fun first(): Step = requireNotNull(firstStep)

    override fun add(step: Step) {
        if (internalMap.isEmpty()) {
            firstStep = step
        }
        internalMap[step.id] = step
    }

    override fun next(stepId: String, userInput: String): Step? {
        return internalMap[stepId]?.let {
            val next = if (it.reply is Reply.Inline) {
                it.reply.options[userInput]
            } else {
                it.reply.options[Reply.ANY_ANSWER_ACCEPTED]
            }
            internalMap[next]
        }
    }
}
