package org.ua.wohnung.bot.gateway

import mu.KotlinLogging
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Update
import org.ua.wohnung.bot.exception.ServiceException
import org.ua.wohnung.bot.flows.FlowRegistry
import org.ua.wohnung.bot.flows.dto.ChatMetadata
import org.ua.wohnung.bot.flows.step.Step
import org.ua.wohnung.bot.persistence.generated.tables.pojos.Account

class MessageGateway(
    private val secret: String,
    private val flowRegistry: FlowRegistry,
    private val session: Session,
    private val messageFactory: MessageFactory
) : TelegramLongPollingBot() {
    private val logger = KotlinLogging.logger {}

    override fun getBotToken(): String = secret

    override fun getBotUsername(): String = "UA_Wohnung_Bot"

    override fun onUpdateReceived(update: Update) {
        logger.info { "Received update, chatId: ${update.message.chatId}" }
        if (update.isProcessable()) {
            val chatMetadata = update.metadata()
            val currentStep = resolveCurrentStep(chatMetadata)

            runCatching {
                currentStep?.postProcessor?.invoke(chatMetadata.toAccount(), chatMetadata.input)

                val flow = flowRegistry.getFlowByUserId(chatMetadata.userId)
                val nextStep = flow.next(
                    currentStep = currentStep?.id, userInput = chatMetadata.input
                ) ?: flow.first()

                nextStep.preProcessor.invoke(chatMetadata.toAccount(), chatMetadata.input)

                val sendMessages = messageFactory.get(chatMetadata.toAccount(), nextStep)
                sendMessages.forEach { execute(it) }
                session.updateState(chatMetadata.chatId, nextStep.id)
            }.onFailure {
                logger.error { it }
                val userMessage = if (it is ServiceException && it.userMessage.isNotEmpty())
                    it.userMessage
                else
                    "Невірно введені дані, ${it.message}"
                val errorMessage = messageFactory
                    .getCustom(chatMetadata.chatId, userMessage)
                execute(errorMessage)
                if (it is ServiceException && it.finishSession)
                    session.dropSession(chatMetadata.chatId)
            }
        }
    }

    private fun resolveCurrentStep(chatMetadata: ChatMetadata): Step? {
        val flow = flowRegistry.getFlowByUserId(chatMetadata.userId)
        return session.current(chatMetadata.chatId)?.let { stepId ->
            flow.current(stepId)
        }
    }

    private fun Update.isProcessable(): Boolean =
        hasCallbackQuery() || (hasMessage() && message.hasText() && message.isUserMessage)

    private fun Update.metadata(): ChatMetadata =
        if (hasMessage()) {
            ChatMetadata(
                userId = message.from.id.toInt(),
                username = message.chat.userName,
                chatId = message.chatId.toInt(),
                input = message.text ?: this.callbackQuery.data
            )
        } else if (hasCallbackQuery()) {
            ChatMetadata(
                userId = callbackQuery.from.id.toInt(),
                username = callbackQuery.from.userName,
                chatId = callbackQuery.message.chatId.toInt(),
                input = callbackQuery.data
            )
        } else throw ServiceException.UnreadableMessage(updateId)

    private fun ChatMetadata.toAccount(): Account =
        Account(userId, chatId, username, null)
}
