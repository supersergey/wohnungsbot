package org.ua.wohnung.bot.flows.step

import org.ua.wohnung.bot.flows.Flow
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.user.model.Role

class UserRegistrationFlow : Flow {

    override val supportedRole = Role.GUEST
    override val first = FlowStep.CONVERSATION_START

//    override fun initialize() {
//        stepFactory.multipleButtons(
//            id = FlowStep.CONVERSATION_START,
//            ReplyOption("Зареєструватися", FlowStep.ACCEPT_POLICIES),
//            ReplyOption("Сайт фонду", FlowStep.FORWARD_TO_WEB)
//        ).addSingle()
//        stepFactory.multipleButtons(
//            id = FlowStep.ACCEPT_POLICIES,
//            ReplyOption("Так", FlowStep.PERSONAL_DATA_PROCESSING_APPROVAL),
//            ReplyOption("Ні", FlowStep.CONVERSATION_FINISHED_DECLINED),
//            ReplyOption("Сайт фонду", FlowStep.FORWARD_TO_WEB),
//            ReplyOption("Почати заново", FlowStep.CONVERSATION_START)
//        ).addSingle()
//        stepFactory.multipleButtons(
//            id = FlowStep.PERSONAL_DATA_PROCESSING_APPROVAL,
//            ReplyOption("Так", FlowStep.BUNDESLAND_SELECTION),
//            ReplyOption("Ні", FlowStep.CONVERSATION_FINISHED_DECLINED),
//            ReplyOption("Сайт фонду", FlowStep.FORWARD_TO_WEB),
//            ReplyOption("Почати заново", FlowStep.CONVERSATION_START)
//        ).addSingle()
//        stepFactory.multipleButtons(
//            id = FlowStep.BUNDESLAND_SELECTION,
//            *BundesLand.values().map { it.germanName }.allTo(FlowStep.DISTRICT_SELECTION),
//            ReplyOption("Сайт фонду", FlowStep.FORWARD_TO_WEB),
//            ReplyOption("Почати заново", FlowStep.CONVERSATION_START)
//        ).addSingle()
//        stepFactory.singleReply(
//            id = FlowStep.DISTRICT_SELECTION,
//            next = FlowStep.FAMILY_COUNT,
//            ReplyOption("Сайт фонду", FlowStep.FORWARD_TO_WEB),
//            ReplyOption("Почати заново", FlowStep.CONVERSATION_START)
//        ).addSingle()
//        stepFactory.multipleButtons(
//            id = FlowStep.FAMILY_COUNT,
//            *(1..12).map { "$it" }.allTo(FlowStep.FAMILY_MEMBERS),
//            ReplyOption("Сайт фонду", FlowStep.FORWARD_TO_WEB),
//            ReplyOption("Почати заново", FlowStep.CONVERSATION_START)
//        ).addSingle()
//        stepFactory.singleReply(
//            id = FlowStep.FAMILY_MEMBERS,
//            next = FlowStep.FIRSTNAME_AND_LASTNAME,
//            ReplyOption("Сайт фонду", FlowStep.FORWARD_TO_WEB),
//            ReplyOption("Почати заново", FlowStep.CONVERSATION_START)
//        ).addSingle()
//        stepFactory.singleReply(
//            id = FlowStep.FIRSTNAME_AND_LASTNAME,
//            next = FlowStep.PHONE_NUMBER,
//            ReplyOption("Сайт фонду", FlowStep.FORWARD_TO_WEB),
//            ReplyOption("Почати заново", FlowStep.CONVERSATION_START)
//        ).addSingle()
//        stepFactory.singleReply(
//            id = FlowStep.PHONE_NUMBER,
//            next = FlowStep.PETS,
//            ReplyOption("Сайт фонду", FlowStep.FORWARD_TO_WEB),
//            ReplyOption("Почати заново", FlowStep.CONVERSATION_START)
//        ).addSingle()
//        stepFactory.multipleButtons(
//            id = FlowStep.PETS,
//            *listOf("Так", "Ні").allTo(FlowStep.FOREIGN_LANGUAGES),
//            ReplyOption("Сайт фонду", FlowStep.FORWARD_TO_WEB),
//            ReplyOption("Почати заново", FlowStep.CONVERSATION_START)
//        ).addSingle()
//        stepFactory.singleReply(
//            id = FlowStep.FOREIGN_LANGUAGES,
//            next = FlowStep.READY_TO_MOVE,
//            ReplyOption("Сайт фонду", FlowStep.FORWARD_TO_WEB),
//            ReplyOption("Почати заново", FlowStep.CONVERSATION_START)
//        ).addSingle()
//        stepFactory.multipleButtons(
//            id = FlowStep.READY_TO_MOVE,
//            *listOf("Так", "Ні").allTo(FlowStep.ALLERGIES),
//            ReplyOption("Сайт фонду", FlowStep.FORWARD_TO_WEB),
//            ReplyOption("Почати заново", FlowStep.CONVERSATION_START)
//        ).addSingle()
//        stepFactory.singleReply(
//            id = FlowStep.ALLERGIES,
//            next = FlowStep.REGISTERED_USER_CONVERSATION_START,
//            ReplyOption("Сайт фонду", FlowStep.FORWARD_TO_WEB),
//            ReplyOption("Почати заново", FlowStep.CONVERSATION_START)
//        ).addSingle()
//        stepFactory.termination(FlowStep.FORWARD_TO_WEB).addSingle()
//        stepFactory.termination(FlowStep.CONVERSATION_FINISHED_DECLINED).addSingle()
//    }
}
