package org.ua.wohnung.bot.flows.owner

import org.ua.wohnung.bot.flows.Flow
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.user.model.Role

class OwnerFlow : Flow {
    override val supportedRole = Role.OWNER
    override val first: FlowStep = FlowStep.INITIAL

//    override fun initialize() {
//        val startReplyOptions = arrayOf(
//            ReplyOption("/updateTable", FlowStep.OWNER_APARTMENTS_LOADED, "(оновити таблицю)"),
//            ReplyOption("/listAdmins", FlowStep.OWNER_LIST_ADMINS, "(подивитись список адмінів)"), ReplyOption("/addAdmin", FlowStep.OWNER_ADD_ADMIN, "(призначити користувача адміном)"),
//            ReplyOption("/removeAdmin", FlowStep.OWNER_REMOVE_ADMIN, "видалити користувача з адмінів"),
//            ReplyOption("/whoIsInterested", FlowStep.ADMIN_WHO_IS_INTERESTED_ASK_APARTMENT_ID, "Хто цікавиться житлом")
//        )
//
//        stepFactory.multipleTextOptions(
//            FlowStep.OWNER_START,
//            *startReplyOptions
//        ).addSingle()
//        stepFactory.multipleTextOptions(
//            FlowStep.OWNER_APARTMENTS_LOADED,
//            *startReplyOptions
//        ).addSingle()
//        stepFactory.multipleTextOptions(
//            FlowStep.OWNER_LIST_ADMINS,
//            *startReplyOptions
//        ).addSingle()
//        stepFactory.singleReply(FlowStep.OWNER_ADD_ADMIN, FlowStep.OWNER_ADD_ADMIN_DONE).addSingle()
//        stepFactory.singleReply(FlowStep.OWNER_REMOVE_ADMIN, FlowStep.OWNER_ADD_ADMIN_DONE).addSingle()
//        stepFactory.multipleTextOptions(
//            FlowStep.OWNER_ADD_ADMIN_DONE,
//            *startReplyOptions
//        ).addSingle()
//        stepFactory.singleReply(
//            FlowStep.ADMIN_WHO_IS_INTERESTED_ASK_APARTMENT_ID,
//            FlowStep.ADMIN_LIST_APPLICANTS
//        ).addSingle()
//        stepFactory.multipleTextOptions(
//            FlowStep.ADMIN_LIST_APPLICANTS,
//            *startReplyOptions
//        ).addSingle()
//    }
}
