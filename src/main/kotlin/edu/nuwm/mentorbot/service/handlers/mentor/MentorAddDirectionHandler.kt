package edu.nuwm.mentorbot.service.handlers.mentor

import edu.nuwm.mentorbot.persistence.DirectionsRepository
import edu.nuwm.mentorbot.persistence.UsersRepository
import edu.nuwm.mentorbot.persistence.entities.Direction
import edu.nuwm.mentorbot.persistence.entities.State
import edu.nuwm.mentorbot.persistence.entities.User
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage

@Component
class MentorAddDirectionHandler(
        private val usersRepository: UsersRepository,
        private val directionsRepository: DirectionsRepository
) : AbstractMentorHandler(usersRepository) {

    override fun getMentorMessage(user: User, inputMessage: String): SendMessage {

        val directionExists = createDirection(user, inputMessage)
        return when (directionExists) {
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

    private fun createDirection(user: User, directionName: String): Boolean {
        var direction = directionsRepository.findByIdOrNull(directionName)
        var newDirection = false

        if (direction == null) {
            direction = directionsRepository.save(Direction(directionName))
            newDirection = true
        }
        direction.mentors[user.userId] = ""
        directionsRepository.save(direction)
        return newDirection
    }

    companion object {
        const val NEW_DIRECTION = "Додано новий напрям. Дай нам трохи часу на перевірку"
        const val EXISTED_DIRECTION = "Напрям додано! Додай інформацію про себе"
        const val NEXT_TIME = "Іншим разом"
    }
}