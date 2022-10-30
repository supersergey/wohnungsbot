package org.ua.wohnung.bot.dto

data class ChatMetadata(
    val userId: Long,
    val chatId: Long,
    val messageId: Int,
    val username: String?,
    val input: String,
    val meta: String? = null
)