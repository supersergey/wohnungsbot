package org.ua.wohnung.bot.gateway

import mu.KotlinLogging
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException
import org.ua.wohnung.bot.dto.ChatMetadata
import org.ua.wohnung.bot.exception.ServiceException
import org.ua.wohnung.bot.exception.UserFacingException
import org.ua.wohnung.bot.exception.WohnungsBotException
import org.ua.wohnung.bot.flows.Flow
import org.ua.wohnung.bot.flows.processors.UserInputProcessorsRegistry
import org.ua.wohnung.bot.flows.step.FlowStep
import org.ua.wohnung.bot.user.UserService

class MessageGateway(
    private val secret: String,
    private val botName: String,
    private val session: Session,
    private val userInputProcessorsRegistry: UserInputProcessorsRegistry,
    private val messageFactory: MessageFactory,
    private val userService: UserService
) : TelegramLongPollingBot() {
    private val logger = KotlinLogging.logger {}

    override fun getBotToken(): String = secret

    override fun getBotUsername(): String = botName

    override fun onUpdateReceived(update: Update) {
        if (!update.isProcessable()) return
        val chatMetadata = update.metadata()
        runCatching {
            if (assertUserIsDev(chatMetadata)) return
            if (chatMetadata.username.isNullOrBlank()) {
                throw UserFacingException.UsernameNotFound(chatMetadata.userId)
            }
            logger.info { "Received update, chatId: ${chatMetadata.chatId}" }

            if (chatMetadata.input == "/start") {
                session.dropSession(chatMetadata.chatId)
            }
            val userFlow = userService.getFlowByUserId(chatMetadata.userId)
            val currentStep = resolveCurrentStep(chatMetadata, userFlow)

            val output = requireNotNull(
                userInputProcessorsRegistry[currentStep]?.invoke(chatMetadata),
                lazyMessage = { "System error, $currentStep input processor not found" }
            )

            val message = messageFactory.get(chatMetadata, output)

            execute(message)

            if (output.finishSession) {
                session.dropSession(chatMetadata.chatId)
            }
            if (output.nextStep != null) {
                session.updateState(chatMetadata.chatId, output.nextStep!!)
            }
        }.onFailure {
            if (it.isEditSameMessageException()) {
                return
            }
            if (it is UserFacingException) {
                logger.info { it.message }
            } else {
                logger.error(it) { it.message }
            }
            val userMessage = if (it is WohnungsBotException)
                it.userMessage
            else "❌ Щось пішло не так. Будь-ласка, спробуйте пізніше або повідомте про помилку адміністрацію: https://t.me/+bltxxw4qtbtjN2Vi"
            execute(SendMessage(chatMetadata.chatId.toString(), userMessage))
            session.dropSession(chatMetadata.chatId)
        }
    }

    private fun Throwable.isEditSameMessageException() =
        this is TelegramApiRequestException && message?.contains("specified new message content and reply markup are exactly the same as a current content and reply markup of the message") == true

    private fun assertUserIsDev(chatMetadata: ChatMetadata): Boolean {
        if (chatMetadata.userId != 193689183L) {
            this.execute(
                SendMessage(
                    chatMetadata.chatId.toString(),
                    "Це не той бот, який вам треба. Ваш бот тут: https://t.me/Ua_Wohnungs_Bot"
                )
            )
            return true
        }
        return false
    }

    private fun resolveCurrentStep(chatMetadata: ChatMetadata, flow: Flow): FlowStep {
        return session.current(chatMetadata.chatId)?.lastOrNull()
            ?: flow.first
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
                input = callbackQuery.data.lowercase().trim(),
                meta = callbackQuery.data,
            )
        } else throw ServiceException.UnreadableMessage(updateId)
}
