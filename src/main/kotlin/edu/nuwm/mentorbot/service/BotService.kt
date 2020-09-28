package edu.nuwm.mentorbot.service

import edu.nuwm.mentorbot.persistence.UserStatesRepository
import edu.nuwm.mentorbot.persistence.entities.State
import edu.nuwm.mentorbot.service.handlers.InitHandler
import edu.nuwm.mentorbot.service.handlers.SetTypeHandler
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Message

@Service
class BotService(
        private val userStatesRepository: UserStatesRepository,
        private val initHandler: InitHandler,
        private val setTypeHandler: SetTypeHandler
) {

    fun processMessage(message: Message): SendMessage {
        val chatId = message.chatId
        val messageText = message.text

        val userState = userStatesRepository.findById(chatId)
        val state = userState.map { it.state }.orElse(State.INIT)

        return when (state) {
            State.INIT -> initHandler.buildMessage(chatId)
            State.SET_TYPE -> setTypeHandler.buildMessage(chatId, messageText)
            else -> SendMessage(chatId, "Спробуй ще раз")
        }
    }
}