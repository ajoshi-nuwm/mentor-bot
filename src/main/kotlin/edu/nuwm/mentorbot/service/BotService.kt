package edu.nuwm.mentorbot.service

import edu.nuwm.mentorbot.persistence.UserStatesRepository
import edu.nuwm.mentorbot.persistence.entities.States
import edu.nuwm.mentorbot.service.handlers.InitHandler
import edu.nuwm.mentorbot.service.handlers.SetTypeHandler
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage

@Service
class BotService(private val userStatesRepository: UserStatesRepository,
                 private val initHandler: InitHandler,
                 private val setTypeHandler: SetTypeHandler) {

    fun processMessage(chatId: Long?, messageText: String?): SendMessage {
        val userState = chatId?.let { userStatesRepository.findById(it) }
        val state = userState?.map { t -> t.state }?.orElse(States.INIT)

        return when (state) {
            States.INIT -> initHandler.buildMessage(chatId)
            States.SET_TYPE -> setTypeHandler.buildMessage(chatId, messageText)
            else -> SendMessage(chatId, "Спробуй ще раз")
        }
    }
}