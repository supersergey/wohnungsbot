//package org.ua.wohnung.bot.flows.processors.registereduser.stepfactory
//
//import org.ua.wohnung.bot.configuration.MessageSource
//import org.ua.wohnung.bot.flows.dto.ChatMetadata
//import org.ua.wohnung.bot.flows.processors.userregistration.stepfactory.AbstractStepFactory
//import org.ua.wohnung.bot.flows.processors.userregistration.userinputprocessor.Message
//import org.ua.wohnung.bot.flows.processors.userregistration.userinputprocessor.StepOutput
//import org.ua.wohnung.bot.flows.step.FlowStep
//import org.ua.wohnung.bot.user.UserService
//
//class RegisteredUserConversationFinishRemovalStepFactory(userService: UserService, messageSource: MessageSource): AbstractStepFactory(userService, messageSource) {
//    override val supportedStep: FlowStep = FlowStep.CONVERSATION_FINISH_REMOVAL
//
//    override fun doInvoke(chatMetadata: ChatMetadata): StepOutput {
//        return StepOutput.PlainText(
//            message = Message(messageSource[supportedStep]),
//            nextStep = FlowStep.INITIAL,
//            finishSession = true
//        )
//    }
//}