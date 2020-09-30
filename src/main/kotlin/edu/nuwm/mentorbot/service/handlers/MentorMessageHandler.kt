package edu.nuwm.mentorbot.service.handlers

import edu.nuwm.mentorbot.persistence.DirectionsRepository
import edu.nuwm.mentorbot.persistence.UsersRepository
import edu.nuwm.mentorbot.persistence.entities.State
import edu.nuwm.mentorbot.persistence.entities.User
import edu.nuwm.mentorbot.service.controls.keyboards.ButtonConstants.Companion.MENTOR_ADD_DIRECTION
import edu.nuwm.mentorbot.service.controls.keyboards.ButtonConstants.Companion.MENTOR_GET_MY_DIRECTIONS
import edu.nuwm.mentorbot.service.controls.keyboards.ButtonConstants.Companion.MENTOR_GET_OTHER_MENTORS
import edu.nuwm.mentorbot.service.handlers.MessageHandler.Companion.ERROR_GREETING
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.ParseMode
import org.telegram.telegrambots.meta.api.methods.send.SendMessage

@Component
class MentorMessageHandler(
        private val usersRepository: UsersRepository,
        private val directionsRepository: DirectionsRepository
) : MessageHandler {

    override fun getMessage(user: User, inputMessage: String): SendMessage {
        return when (inputMessage) {
            MENTOR_ADD_DIRECTION -> {
                user.state = State.MENTOR_ADD_DIRECTION
                usersRepository.save(user)
                SendMessage(user.chatId, ADDING_DIRECTION + "\n\n" + getDirections()).setParseMode(ParseMode.MARKDOWN)
            }
            MENTOR_GET_MY_DIRECTIONS -> {
                user.state = State.MENTOR_DEFAULT
                usersRepository.save(user)
                SendMessage(user.chatId, GETTING_MY_DIRECTIONS)
            }
            MENTOR_GET_OTHER_MENTORS -> {
                user.state = State.MENTOR_DEFAULT
                usersRepository.save(user)
                SendMessage(user.chatId, GETTING_OTHER_MENTORS)
            }
            else -> SendMessage(user.chatId, ERROR_GREETING)
        }
    }

    fun getDirections(): String {
        return directionsRepository.findByActiveTrue().joinToString("\n") { "`${it.name}`" }
    }

    companion object {
        const val ADDING_DIRECTION = "Обери серед запропонованих напрямів або додай свій:"
        const val GETTING_MY_DIRECTIONS = "Мої напрями"
        const val GETTING_OTHER_MENTORS = "Шукаємо інших менторів"
    }
}