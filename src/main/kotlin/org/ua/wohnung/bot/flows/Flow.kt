package org.ua.wohnung.bot.flows

import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.user.model.Role

interface Flow {
    val supportedRole: Role
    val first: FlowStep
}
// abstract class Flow {
//    private val internalMap = mutableMapOf<FlowStep, Step>()
//
//    private var first: Step? = null
//
//    abstract val supportedRole: Role
//
//    open fun first(username: String = "") = requireNotNull(first)
//
//    fun current(flowStep: FlowStep): Step = internalMap[flowStep] ?: first()
//
//    fun next(currentStep: FlowStep?, userInput: String): Step? {
//        if (currentStep == null) {
//            return first
//        }
//        return internalMap[currentStep]?.let {
//            val next = when (it.reply) {
//                is Reply.WithButtons -> it.reply.options[userInput]
//                is Reply.MultiText -> it.reply.options[userInput]
//                is Reply.WithDynamicButtons -> it.reply.options[Reply.ANY_ANSWER_ACCEPTED]
//                else -> it.reply.options[Reply.ANY_ANSWER_ACCEPTED]
//            }
//            internalMap[next?.nextStep]
//        }
//    }
//
//    private fun add(step: Step) {
//        if (internalMap.isEmpty()) {
//            first = step
//        }
//        internalMap[step.id] = step
//    }
//
//    abstract fun initialize()
//
//    protected fun <T : Step> T.addSingle() {
//        add(this)
//    }
//
//    protected fun List<String>.allTo(next: FlowStep): Array<ReplyOption> =
//        map { ReplyOption(it, next) }.toTypedArray()
// }
