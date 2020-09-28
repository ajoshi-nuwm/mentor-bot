package edu.nuwm.mentorbot.api.bot

import edu.nuwm.mentorbot.service.BotService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Update

@Component
class MentorBot(
        private val botService: BotService
) : TelegramLongPollingBot() {

    @Value("\${telegram.bot.username}")
    lateinit var username: String

    @Value("\${telegram.bot.token}")
    lateinit var token: String

    override fun getBotUsername(): String = username

    override fun getBotToken(): String = token

    override fun onUpdateReceived(update: Update) {
        val message = update.message ?: throw IllegalArgumentException("Chat id is empty")
        execute(botService.processMessage(message))
    }
}