package edu.nuwm.mentorbot.service.handlers.mentor

import edu.nuwm.mentorbot.persistence.DirectionsRepository
import edu.nuwm.mentorbot.persistence.UsersRepository
import edu.nuwm.mentorbot.persistence.entities.State
import edu.nuwm.mentorbot.persistence.entities.User
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage

@Component
class MentorAddExperienceHandler(
        private val usersRepository: UsersRepository,
        private val directionsRepository: DirectionsRepository
) : AbstractMentorHandler(usersRepository) {

    override fun getMentorMessage(user: User, inputMessage: String): SendMessage {
        val directionName = user.context["direction"] ?: throw Exception("Direction not found in a context")
        val direction = directionsRepository.findByIdOrNull(directionName)
                ?: throw Exception("Direction not found: $directionName")
        direction.mentors[user.userId] = inputMessage
        user.state = State.MENTOR_DEFAULT
        user.context["directions"] = "${user.context["directions"]}:${direction.name}"

        directionsRepository.save(direction)
        usersRepository.save(user)
        return SendMessage(user.chatId, INFORMATION_ADDED)
    }


    companion object {
        const val INFORMATION_ADDED = "Інформацію додано! Чекай на студентів!"
    }
}