//package org.ua.wohnung.bot.flows.registereduser.processors
//
//import org.ua.wohnung.bot.apartment.ApartmentService
//import org.ua.wohnung.bot.configuration.MessageSource
//import org.ua.wohnung.bot.flows.AbstractUserInputProcessor
//import org.ua.wohnung.bot.flows.dto.ChatMetadata
//import org.ua.wohnung.bot.flows.processors.Message
//import org.ua.wohnung.bot.flows.processors.StepOutput
//import org.ua.wohnung.bot.flows.step.FlowStep
//import org.ua.wohnung.bot.flows.step.FlowStep.INITIAL
//import org.ua.wohnung.bot.persistence.generated.tables.pojos.Apartment
//import org.ua.wohnung.bot.user.UserService
//
//class RegisteredUserConversationStartInputProcessor(
//    private val apartmentService: ApartmentService,
//    userService: UserService,
//    messageSource: MessageSource
//) : RegisteredUserInputProcessor(userService, messageSource) {
//    override val supportedStep: FlowStep = INITIAL
//
//    override fun processSpecificCommands(chatMetadata: ChatMetadata): StepOutput? {
//
//}
