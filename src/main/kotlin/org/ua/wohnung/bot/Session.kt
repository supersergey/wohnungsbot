package org.ua.wohnung.bot

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

class Session {

    // userId to StepId
    private val internalMap: ConcurrentMap<Long, String> = ConcurrentHashMap()

    fun current(chatId: Long): String? = internalMap[chatId]

    fun updateState(chatId: Long, state: String) {
        internalMap[chatId] = state
    }

    fun dropSession(chatId: Long) {
        internalMap.remove(chatId)
    }
}
