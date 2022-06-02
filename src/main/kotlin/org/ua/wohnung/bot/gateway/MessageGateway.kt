package org.ua.wohnung.bot.gateway

import mu.KotlinLogging
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Update
import org.ua.wohnung.bot.flows.FlowRegistry
import org.ua.wohnung.bot.flows.dto.UserInput
import org.ua.wohnung.bot.flows.processors.ProcessorContainer

class MessageGateway(
    private val secret: String,
    private val flowRegistry: FlowRegistry,
    private val session: Session,
    private val messageFactory: MessageFactory,
    private val messagePreProcessors: ProcessorContainer.MessagePreProcessors
) : TelegramLongPollingBot() {
    private val logger = KotlinLogging.logger {}

    override fun getBotToken(): String = secret

    override fun getBotUsername(): String = "UA_Wohnung_Bot"

    override fun onUpdateReceived(update: Update) {
        logger.info { "Received update, chatId: ${update.message.chatId}" }
        if (update.isProcessable()) {
            val incomingMessage = update.input()
            val flow = flowRegistry.getFlowByUserId(incomingMessage.username)
            val currentStep = session.current(incomingMessage.chatId)?.let { stepId ->
                flow.current(stepId)
            } ?: flow.first()

            runCatching {
                currentStep.postProcessor(incomingMessage.username, incomingMessage.input)

                val nextStep = flow.next(
                    currentStep = currentStep.id, userInput = incomingMessage.input
                ) ?: flow.first()

                nextStep.preProcessor(incomingMessage.username, incomingMessage.input)

                val sendMessage = messageFactory.newStepMessage(incomingMessage.chatId, nextStep)
                sendMessage.text = messagePreProcessors[nextStep.id].invoke(incomingMessage.username, sendMessage.text) as String
                execute(sendMessage)
                session.updateState(incomingMessage.chatId, nextStep.id)
            }.onFailure {
                println(it.stackTraceToString())
                logger.error { it }
                val errorMessage = messageFactory
                    .newCustomMessage(incomingMessage.chatId, "Невірно введені дані, ${it.message}")
                execute(errorMessage)
            }
        }
    }

    private fun Update.isProcessable(): Boolean =
        hasMessage() && message.hasText() && message.isUserMessage

    private fun Update.input(): UserInput =
        UserInput(
            username = message.chat.userName,
            chatId = message.chatId,
            input = message.text
        )
}
