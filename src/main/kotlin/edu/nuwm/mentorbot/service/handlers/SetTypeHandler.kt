package edu.nuwm.mentorbot.service.handlers

import edu.nuwm.mentorbot.persistence.UserStatesRepository
import edu.nuwm.mentorbot.persistence.entities.State
import edu.nuwm.mentorbot.persistence.entities.UserState
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage

@Component
class SetTypeHandler(
        private val userStatesRepository: UserStatesRepository
) : Handler {

    override fun getMessage(chatId: Long, messageText: String): SendMessage {
        return when (messageText.toUpperCase()) {
            "МЕНТОР!" -> {
                userStatesRepository.save(UserState(chatId, State.MENTOR_DEFAULT))
                SendMessage(chatId, "Привіт Ментор!")
            }
            "СТУДЕНТ!" -> {
                userStatesRepository.save(UserState(chatId, State.STUDENT_DEFAULT))
                SendMessage(chatId, "Привіт Студент!")
            }
            else -> SendMessage(chatId, "Не зрозумів, давай ще раз")
        }
    }
}