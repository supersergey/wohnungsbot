package org.ua.wohnung.bot.gateway

import org.ua.wohnung.bot.flows.step.FlowStep
import java.time.Duration
import java.time.Instant
import java.util.Timer
import java.util.TimerTask
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

class Session(private val sessionTtl: Duration = Duration.ofMinutes(15)) {

    // userId to StepId
    private val internalMap: ConcurrentMap<Long, StepSession> = ConcurrentHashMap()
    private val timer: Timer = Timer(true)

    private data class StepSession(
        val steps: MutableList<FlowStep>,
        var lastUpdateTs: Instant = Instant.now()
    )

    init {
        timer.schedule(
            SessionExpireTask(),
            0,
            sessionTtl.toMillis()
        )
    }

    fun current(chatId: Long): List<FlowStep>? = internalMap[chatId]?.steps?.toList()

    fun updateState(chatId: Long, state: FlowStep) {
        internalMap.computeIfAbsent(chatId) {
            StepSession(mutableListOf(state))
        }.steps.add(state)
        internalMap[chatId]?.lastUpdateTs = Instant.now()
    }


    fun dropSession(vararg chatId: Long) {
        chatId.forEach { internalMap.remove(it) }
    }

    inner class SessionExpireTask : TimerTask() {
        override fun run() {
            internalMap.entries.filter {
                Duration.between(it.value.lastUpdateTs, Instant.now()) > sessionTtl
            }.map { it.key }.forEach {
                internalMap.remove(it)
            }
        }
    }
}
