package edu.nuwm.mentorbot.service.handlers

import edu.nuwm.mentorbot.persistence.UsersRepository
import edu.nuwm.mentorbot.persistence.entities.State
import edu.nuwm.mentorbot.persistence.entities.User
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage

@Component
class InitMessageHandler(
        private val usersRepository: UsersRepository
) : MessageHandler {

    override fun getMessage(user: User, inputMessage: String): SendMessage {
        user.state = State.SET_TYPE
        usersRepository.save(user)
        return SendMessage(user.chatId, "Привіт! Радий бачити тебе тут. Ти ментор чи студент?")
    }
}