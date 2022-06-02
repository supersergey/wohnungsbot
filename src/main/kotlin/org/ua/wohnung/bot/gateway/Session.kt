package org.ua.wohnung.bot.gateway

import org.ua.wohnung.bot.flows.FlowStep
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

class Session {

    // userId to StepId
    private val internalMap: ConcurrentMap<Long, FlowStep> = ConcurrentHashMap()

    fun current(chatId: Long): FlowStep? = internalMap[chatId]

    fun updateState(chatId: Long, state: FlowStep) {
        internalMap[chatId] = state
    }

    fun dropSession(chatId: Long) {
        internalMap.remove(chatId)
    }
}
