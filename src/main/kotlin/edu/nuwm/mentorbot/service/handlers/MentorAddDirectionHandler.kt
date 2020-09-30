package edu.nuwm.mentorbot.service.handlers

import edu.nuwm.mentorbot.persistence.DirectionsRepository
import edu.nuwm.mentorbot.persistence.UsersRepository
import edu.nuwm.mentorbot.persistence.entities.Direction
import edu.nuwm.mentorbot.persistence.entities.State
import edu.nuwm.mentorbot.persistence.entities.User
import edu.nuwm.mentorbot.service.controls.keyboards.ButtonConstants.Companion.BACK
import edu.nuwm.mentorbot.unwrap
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage

@Component
class MentorAddDirectionHandler(
        private val usersRepository: UsersRepository,
        private val directionsRepository: DirectionsRepository
) : MessageHandler {

    override fun getMessage(user: User, inputMessage: String): SendMessage {

        if (inputMessage == BACK) {
            user.state = State.MENTOR_DEFAULT
            usersRepository.save(user)
            return SendMessage(user.chatId, NEXT_TIME)
        }

        var direction = directionsRepository.findById(inputMessage).unwrap()
        var newDirection = false

        if (direction == null) {
            direction = directionsRepository.save(Direction(inputMessage))
            newDirection = true
        }
        direction.mentors[user.userId] = ""
        directionsRepository.save(direction)
        return when (newDirection) {
            true -> {
                user.state = State.MENTOR_DEFAULT
                usersRepository.save(user)
                SendMessage(user.chatId, NEW_DIRECTION)
            }
            false -> {
                user.state = State.MENTOR_ADD_DIRECTION_INFORMATION
                usersRepository.save(user)
                SendMessage(user.chatId, EXISTED_DIRECTION)
            }
        }
    }

    companion object {
        const val NEW_DIRECTION = "Додано новий напрям. Дай нам трохи часу на перевірку"
        const val EXISTED_DIRECTION = "Напрям додано! Додай інформацію про себе"
        const val NEXT_TIME = "Іншим разом"
    }
}