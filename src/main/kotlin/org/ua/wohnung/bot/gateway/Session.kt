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
    private val internalMap: ConcurrentMap<Int, StepSession> = ConcurrentHashMap()
    private val timer: Timer = Timer(true)

    private data class StepSession(val flowStep: FlowStep, val lastUpdateTs: Instant = Instant.now())

    init {
        timer.schedule(
            SessionExpireTask(),
            0,
            sessionTtl.toMillis()
        )
    }

    fun current(chatId: Int): FlowStep? = internalMap[chatId]?.flowStep

    fun updateState(chatId: Int, state: FlowStep) {
        internalMap[chatId] = StepSession(state)
    }

    fun dropSession(vararg chatId: Int) {
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
