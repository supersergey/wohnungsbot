package org.ua.wohnung.bot.flows.admin

import org.ua.wohnung.bot.flows.Flow
import org.ua.wohnung.bot.flows.FlowStep
import org.ua.wohnung.bot.flows.ReplyOption
import org.ua.wohnung.bot.flows.StepFactory
import org.ua.wohnung.bot.user.model.Role

class AdminFlow(private val stepFactory: StepFactory): Flow() {
    override val supportedRole: Role = Role.ADMIN

    override fun initialize() {
        stepFactory.multipleTextOptions(
            FlowStep.ADMIN_START,
            ReplyOption("/whoIsInterested", FlowStep.ADMIN_WHO_IS_INTERESTED_ASK_APARTMENT_ID, "Хто цікавиться житлом")
        ).addSingle()
        stepFactory.singleReply(
            FlowStep.ADMIN_WHO_IS_INTERESTED_ASK_APARTMENT_ID,
            FlowStep.ADMIN_WHO_IS_INTERESTED
        ).addSingle()
        stepFactory.termination(
            FlowStep.ADMIN_WHO_IS_INTERESTED
        )
    }
}