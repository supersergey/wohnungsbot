package org.ua.wohnung.bot.gateway

import org.ua.wohnung.bot.flows.FlowStep
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

class Session {

    // userId to StepId
    private val internalMap: ConcurrentMap<Int, FlowStep> = ConcurrentHashMap()

    fun current(chatId: Int): FlowStep? = internalMap[chatId]

    fun updateState(chatId: Int, state: FlowStep) {
        internalMap[chatId] = state
    }

    fun dropSession(chatId: Int) {
        internalMap.remove(chatId)
    }
}
