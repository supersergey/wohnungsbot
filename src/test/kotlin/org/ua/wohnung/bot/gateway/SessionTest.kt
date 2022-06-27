package org.ua.wohnung.bot.gateway

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.ua.wohnung.bot.flows.step.FlowStep
import java.time.Duration
import kotlin.random.Random.Default.nextLong

internal class SessionTest {

    private val session = Session(Duration.ofSeconds(1))

    @Test
    fun `should expire old sessions`() {
        val expiredChatId1 = nextLong()
        val expiredChatId2 = nextLong()
        val notExpiredChatId = nextLong()
        session.updateState(expiredChatId1, FlowStep.ADMIN_START)
        session.updateState(expiredChatId2, FlowStep.ADMIN_START)
        assertThat(session.current(expiredChatId1)).isNotNull
        assertThat(session.current(expiredChatId2)).isNotNull
        Thread.sleep(1800)
        session.updateState(notExpiredChatId, FlowStep.ADMIN_START)
        Thread.sleep(450)
        assertThat(session.current(expiredChatId1)).isNull()
        assertThat(session.current(expiredChatId2)).isNull()
        assertThat(session.current(notExpiredChatId)).isNotNull
        Thread.sleep(1000)
        assertThat(session.current(notExpiredChatId)).isNull()
    }
}
