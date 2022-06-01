package org.ua.wohnung.bot.engine

import mu.KotlinLogging
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Update
import org.ua.wohnung.bot.flows.dto.UserInput
import org.ua.wohnung.bot.flows.userregistration.Flow

class WohnungsBot(
    private val secret: String,
    private val userRegistrationFlow: Flow,
    private val session: Session,
    private val messageFactory: MessageFactory
) : TelegramLongPollingBot() {
    private val logger = KotlinLogging.logger {}

    override fun getBotToken(): String = secret

    override fun getBotUsername(): String = "UA_Wohnung_Bot"

    override fun onUpdateReceived(update: Update) {
        if (update.isProcessable()) {
            val incomingMessage = update.input()
            val currentStep = session.current(incomingMessage.chatId)?.let { stepId ->
                userRegistrationFlow.current(stepId)
            } ?: userRegistrationFlow.first()

            kotlin.runCatching {
                currentStep.postProcessor(incomingMessage.username, incomingMessage.input)

                val nextStep = userRegistrationFlow.next(
                    currentStep = currentStep.id, userInput = incomingMessage.input
                ) ?: userRegistrationFlow.first()

                nextStep.preProcessor(incomingMessage.username, incomingMessage.input)

                val sendMessage = messageFactory.newStepMessage(incomingMessage.chatId, nextStep)
                execute(sendMessage)
                session.updateState(incomingMessage.chatId, nextStep.id)
                nextStep.preProcessor(incomingMessage.username, incomingMessage.input)
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
