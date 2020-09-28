package edu.nuwm.mentorbot.api.bot

import edu.nuwm.mentorbot.service.BotService
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Update

@Component
class MentorBot(private val botService: BotService) : TelegramLongPollingBot() {
    override fun getBotUsername(): String {
        return "NUWEE Mentor Bot"
    }

    override fun getBotToken(): String {
        return "1041495813:AAHiOzjRVsvNsD30lB-qI4IPhbysqhbm9Ig"
    }

    override fun onUpdateReceived(update: Update?) {
        val chatId = update?.message?.chatId
        val messageText = update?.message?.text

        execute(botService.processMessage(chatId, messageText))
    }
}