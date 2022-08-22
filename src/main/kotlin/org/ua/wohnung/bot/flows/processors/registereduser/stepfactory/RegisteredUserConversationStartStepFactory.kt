//package org.ua.wohnung.bot.flows.processors.registereduser.stepfactory
//
//import org.ua.wohnung.bot.configuration.MessageSource
//import org.ua.wohnung.bot.exception.ServiceException
//import org.ua.wohnung.bot.flows.dto.ChatMetadata
//import org.ua.wohnung.bot.flows.processors.userregistration.stepfactory.AbstractStepFactory
//import org.ua.wohnung.bot.flows.processors.userregistration.userinputprocessor.Message
//import org.ua.wohnung.bot.flows.processors.userregistration.userinputprocessor.StepOutput
//import org.ua.wohnung.bot.flows.step.FlowStep
//import org.ua.wohnung.bot.user.UserService
//
//class RegisteredUserConversationStartStepFactory(userService: UserService, messageSource: MessageSource) :
//    AbstractStepFactory(userService, messageSource) {
//    override val supportedStep: FlowStep = FlowStep.REGISTERED_USER_CONVERSATION_START
//
//    override fun doInvoke(chatMetadata: ChatMetadata): StepOutput {
//        val userDetails = userService.findById(chatMetadata.userId)
//            ?: throw ServiceException.UserNotFound(chatMetadata.userId)
//        return StepOutput.PlainText(
//            message = Message(payload = messageSource[supportedStep].format(userDetails.firstLastName)),
//            nextStep = FlowStep.REGISTERED_USER_CONVERSATION_START,
//            finishSession = true
//        )
//    }
//}
