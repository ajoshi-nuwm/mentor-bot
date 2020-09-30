package edu.nuwm.mentorbot.service.handlers.common

import edu.nuwm.mentorbot.persistence.UsersRepository
import edu.nuwm.mentorbot.persistence.entities.State
import edu.nuwm.mentorbot.persistence.entities.User
import edu.nuwm.mentorbot.service.controls.keyboards.ButtonConstants.Companion.MENTOR
import edu.nuwm.mentorbot.service.controls.keyboards.ButtonConstants.Companion.STUDENT
import edu.nuwm.mentorbot.service.handlers.MessageHandler
import edu.nuwm.mentorbot.service.handlers.MessageHandler.Companion.ERROR_GREETING
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage

@Component
class SetTypeMessageHandler(
        private val usersRepository: UsersRepository
        ) : MessageHandler {

    override fun getMessage(user: User, inputMessage: String): SendMessage {
        return when (inputMessage) {
            MENTOR -> {
                user.state = State.MENTOR_DEFAULT
                usersRepository.save(user)
                SendMessage(user.chatId, MENTOR_GREETING)
            }
            STUDENT -> {
                user.state = State.STUDENT_DEFAULT
                usersRepository.save(user)
                SendMessage(user.chatId, STUDENT_GREETING)
            }
            else -> SendMessage(user.chatId, ERROR_GREETING)
        }
    }

    companion object {
        const val MENTOR_GREETING = "Привіт Ментор!"
        const val STUDENT_GREETING = "Привіт Студент!"
    }
}