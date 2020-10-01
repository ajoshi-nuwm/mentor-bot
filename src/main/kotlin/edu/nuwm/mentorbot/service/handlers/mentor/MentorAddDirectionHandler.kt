package edu.nuwm.mentorbot.service.handlers.mentor

import edu.nuwm.mentorbot.persistence.DirectionsRepository
import edu.nuwm.mentorbot.persistence.UsersRepository
import edu.nuwm.mentorbot.persistence.entities.Direction
import edu.nuwm.mentorbot.persistence.entities.State
import edu.nuwm.mentorbot.persistence.entities.User
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage

@Component
class MentorAddDirectionHandler(
        private val usersRepository: UsersRepository,
        private val directionsRepository: DirectionsRepository,
        private val mongoTemplate: MongoTemplate
) : AbstractMentorHandler(usersRepository) {

    override fun getMentorMessage(user: User, inputMessage: String): SendMessage {
        val directionCreation = createDirection(user, inputMessage)
        return when (directionCreation.isNew) {
            true -> {
                user.state = State.MENTOR_DEFAULT
                usersRepository.save(user)
                SendMessage(user.chatId, NEW_DIRECTION)
            }
            false -> {
                user.state = State.MENTOR_ADD_EXPERIENCE
                user.context["direction"] = directionCreation.name
                usersRepository.save(user)
                SendMessage(user.chatId, EXISTED_DIRECTION)
            }
        }
    }

    private fun createDirection(user: User, directionName: String): DirectionCreationResult {
        var newDirection = false

        val directionNumber = directionName.toLongOrNull()
        val direction = when {
            directionNumber != null -> findByNumber(directionNumber)
            else -> {
                val found = directionsRepository.findByIdOrNull(directionName)
                if (found == null) {
                    newDirection = true
                    directionsRepository.save(Direction(directionName))
                } else {
                    found
                }
            }
        }
        direction.mentors[user.userId] = ""
        directionsRepository.save(direction)
        return DirectionCreationResult(newDirection, direction.name)
    }

    private fun findByNumber(number: Long): Direction {
        val query = Query()
        query.addCriteria(Criteria.where("active").`is`(true))
        query.skip(number - 1)
        query.limit(1)
        query.with(Sort.by(Sort.Order.asc("name")))
        return mongoTemplate.findOne(query, Direction::class.java) ?: throw IllegalArgumentException("Wrong number")
    }

    private data class DirectionCreationResult(val isNew: Boolean, val name: String)

    companion object {
        const val NEW_DIRECTION = "Додано новий напрям. Дай нам трохи часу на перевірку"
        const val EXISTED_DIRECTION = "Напрям додано! Додай інформацію про себе"
        const val NEXT_TIME = "Іншим разом"
        const val ERROR = "Не зрозумів, спробуй ще раз"
    }
}