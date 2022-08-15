package org.ua.wohnung.bot.gateway

import mu.KotlinLogging
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.ua.wohnung.bot.exception.ServiceException
import org.ua.wohnung.bot.exception.WohnungsBotException
import org.ua.wohnung.bot.flows.dto.ChatMetadata
import org.ua.wohnung.bot.flows.processors.UserInputProcessorsRegistry
import org.ua.wohnung.bot.flows.processors.userregistration.stepfactory.StepFactoriesRegistry
import org.ua.wohnung.bot.flows.processors.userregistration.userinputprocessor.Message
import org.ua.wohnung.bot.flows.processors.userregistration.userinputprocessor.StepOutput
import org.ua.wohnung.bot.flows.processors.userregistration.userinputprocessor.UserInputProcessingResult
import org.ua.wohnung.bot.flows.step.FlowStep

class MessageGateway(
    private val secret: String,
    private val botName: String,
    private val session: Session,
    private val userInputProcessorsRegistry: UserInputProcessorsRegistry,
    private val stepFactoriesRegistry: StepFactoriesRegistry,
    private val messageFactory: MessageFactory
) : TelegramLongPollingBot() {
    private val logger = KotlinLogging.logger {}

    override fun getBotToken(): String = secret

    override fun getBotUsername(): String = botName

    override fun onUpdateReceived(update: Update) {
        if (update.isProcessable()) {
            val chatMetadata = update.metadata()
            if (chatMetadata.userId != 193689183L) {
                this.execute(
                    SendMessage(
                        chatMetadata.chatId.toString(),
                        "Це не той бот, який вам треба. Ваш бот тут: https://t.me/Ua_Wohnungs_Bot"
                    )
                )
                return
            }
            logger.info { "Received update, chatId: ${chatMetadata.chatId}" }

            runCatching {
                val currentStep = resolveCurrentStep(chatMetadata)
                val previousStep = resolvePreviousStep(chatMetadata)

                val userInputProcessingResult = previousStep?.let {
                    userInputProcessorsRegistry[previousStep]?.invoke(chatMetadata)
                } ?: UserInputProcessingResult.Success

                val output = when (userInputProcessingResult) {
                    is UserInputProcessingResult.Success -> stepFactoriesRegistry[currentStep].invoke(chatMetadata)
                    is UserInputProcessingResult.Error -> StepOutput.Error(
                        message = Message(userInputProcessingResult.message),
                        finishSession = userInputProcessingResult.finishSession
                    )
                    is UserInputProcessingResult.Default -> stepFactoriesRegistry[FlowStep.INITIAL].invoke(chatMetadata)
                }

                val message = messageFactory.get(chatMetadata, output)

//                if (output is StepOutput.PlainText) {
//                    super.execute(DeleteMessage(chatMetadata.chatId.toString(), chatMetadata.messageId))
//                }

                execute(message)

                if (output.finishSession) {
                    session.dropSession(chatMetadata.chatId)
                }
                if (output.nextStep != null) {
                    session.updateState(chatMetadata.chatId, output.nextStep!!)
                }
            }.onFailure {
                logger.error(it) { it.message }
                if (it is WohnungsBotException) {
                    this.execute(
                        SendMessage(chatMetadata.chatId.toString(), it.userMessage)
                    )
                }
                if (it is ServiceException && it.finishSession)
                    session.dropSession(chatMetadata.chatId)
            }
        }
    }

    private fun execute(message: MessageWrapper) {
        if (message.editMessage != null) {
            super.execute(message.editMessage)
        } else {
            super.execute(message.sendMessage)
        }
    }

    private fun execute(message: SendMessage) {
        super.execute(message)
    }

    private fun resolveCurrentStep(chatMetadata: ChatMetadata): FlowStep {
        return session.current(chatMetadata.chatId)?.lastOrNull() ?: FlowStep.INITIAL
    }

    private fun resolvePreviousStep(chatMetadata: ChatMetadata): FlowStep? {
        if (session.current(chatMetadata.chatId)?.lastOrNull() == FlowStep.CONVERSATION_START) {
            return null
        }
        val list = session.current(chatMetadata.chatId)
        return if (list != null && list.size > 1) {
            return list[list.size - 2]
        } else {
            null
        }
    }

    private fun Update.isProcessable(): Boolean =
        hasCallbackQuery() || (hasMessage() && message.hasText() && message.isUserMessage)

    private fun Update.metadata(): ChatMetadata =
        if (hasMessage()) {
            ChatMetadata(
                userId = message.from.id,
                chatId = message.chatId,
                messageId = message.messageId,
                username = message.chat.userName,
                input = message.text.lowercase().trim()
            )
        } else if (hasCallbackQuery()) {
            ChatMetadata(
                userId = callbackQuery.from.id,
                chatId = this.callbackQuery.message.chatId,
                messageId = callbackQuery.message.messageId,
                username = callbackQuery.from.userName,
                input = callbackQuery.data.lowercase().trim()
            )
        } else throw ServiceException.UnreadableMessage(updateId)
}
