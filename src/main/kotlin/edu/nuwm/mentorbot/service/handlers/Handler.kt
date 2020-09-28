package edu.nuwm.mentorbot.service.handlers

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove

interface Handler {

    fun buildMessage(chatId: Long, messageText: String = ""): SendMessage {
        val message = getMessage(chatId, messageText)
        setInitButtons(message)
        return message
    }

    fun getMessage(chatId: Long, messageText: String): SendMessage

    fun setInitButtons(sendMessage: SendMessage) {
        val removeKeyboard = ReplyKeyboardRemove()
        sendMessage.replyMarkup = removeKeyboard
    }
}