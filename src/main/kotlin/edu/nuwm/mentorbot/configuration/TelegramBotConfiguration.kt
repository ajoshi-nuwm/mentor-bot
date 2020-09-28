package edu.nuwm.mentorbot.configuration

import edu.nuwm.mentorbot.api.bot.MentorBot
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.telegram.telegrambots.meta.TelegramBotsApi

@Configuration
class TelegramBotConfiguration(private val mentorBot: MentorBot) {

    @Bean
    fun telegramBotApi(): TelegramBotsApi {
        val telegramBotsApi = TelegramBotsApi()
        telegramBotsApi.registerBot(mentorBot)
        return telegramBotsApi
    }
}