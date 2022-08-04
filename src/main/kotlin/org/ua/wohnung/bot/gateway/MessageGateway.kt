package org.ua.wohnung.bot.gateway

import mu.KotlinLogging
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.ua.wohnung.bot.exception.ServiceException
import org.ua.wohnung.bot.exception.WohnungsBotException
import org.ua.wohnung.bot.flows.FlowRegistry
import org.ua.wohnung.bot.flows.dto.ChatMetadata
import org.ua.wohnung.bot.flows.step.Step

class MessageGateway(
    private val secret: String,
    private val botName: String,
    private val flowRegistry: FlowRegistry,
    private val session: Session,
    private val messageFactory: MessageFactory
) : TelegramLongPollingBot() {
    private val logger = KotlinLogging.logger {}

    override fun getBotToken(): String = secret

    override fun getBotUsername(): String = botName

    override fun onUpdateReceived(update: Update) {
        if (update.isProcessable()) {
            val chatMetadata = update.metadata()
            logger.info { "Received update, chatId: ${update.message.chatId}" }
            val currentStep = resolveCurrentStep(chatMetadata)

            runCatching {
                currentStep?.postProcessor?.invoke(chatMetadata, chatMetadata.input)

                val flow = flowRegistry.getFlowByUserId(chatMetadata.userId)
                val nextStep = flow.next(
                    currentStep = currentStep?.id, userInput = chatMetadata.input
                ) ?: flow.first()

                nextStep.preProcessor.invoke(chatMetadata, chatMetadata.input)

                val sendMessages = messageFactory.get(chatMetadata, nextStep)
                sendMessages.forEach {
                    execute(it)
                    Thread.sleep(1000)
                }
                session.updateState(chatMetadata.chatId, nextStep.id)
            }.onFailure {
                val userMessage = if (it is WohnungsBotException && it.userMessage.isNotEmpty())
                    it.userMessage
                else {
                    logger.error(it) { it.message }
                    "Невірно введені дані, ${it.message}"
                }
                val errorMessage = messageFactory
                    .getCustom(chatMetadata.chatId, userMessage)
                execute(errorMessage)
                if (it is ServiceException && it.finishSession)
                    session.updateState(
                        chatId = chatMetadata.chatId,
                        state = flowRegistry.getFlowByUserId(chatMetadata.userId).first().id
                    )
            }
        }
    }

    fun execute(message: SendMessage) {
        super.execute(message)
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
                userId = message.from.id,
                username = message.chat.userName,
                chatId = message.chatId,
                input = message.text ?: this.callbackQuery.data
            )
        } else if (hasCallbackQuery()) {
            ChatMetadata(
                userId = callbackQuery.from.id,
                username = callbackQuery.from.userName,
                chatId = callbackQuery.message.chatId,
                input = callbackQuery.data
            )
        } else throw ServiceException.UnreadableMessage(updateId)
}
