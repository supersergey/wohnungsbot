package org.ua.wohnung.bot.flows.admin

import org.ua.wohnung.bot.flows.Flow
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.user.model.Role

class AdminFlow : Flow {
    override val supportedRole: Role = Role.ADMIN
    override val first: FlowStep = FlowStep.INITIAL

//    override fun initialize() {
//        stepFactory.multipleTextOptions(
//            FlowStep.ADMIN_START,
//            ReplyOption("/whoIsInterested", FlowStep.ADMIN_WHO_IS_INTERESTED_ASK_APARTMENT_ID, "Хто цікавиться житлом")
//        ).addSingle()
//        stepFactory.singleReply(
//            FlowStep.ADMIN_WHO_IS_INTERESTED_ASK_APARTMENT_ID,
//            FlowStep.ADMIN_LIST_APPLICANTS
//        ).addSingle()
//        stepFactory.multipleTextOptions(
//            FlowStep.ADMIN_LIST_APPLICANTS,
//            ReplyOption("/whoIsInterested", FlowStep.ADMIN_WHO_IS_INTERESTED_ASK_APARTMENT_ID, "Хто цікавиться житлом")
//        ).addSingle()
//    }
}
