package edu.nuwm.mentorbot.service.handlers.mentor

import edu.nuwm.mentorbot.persistence.DirectionsRepository
import edu.nuwm.mentorbot.persistence.UsersRepository
import edu.nuwm.mentorbot.persistence.entities.State
import edu.nuwm.mentorbot.persistence.entities.User
import edu.nuwm.mentorbot.service.controls.keyboards.ButtonConstants.Companion.MENTOR_ADD_DIRECTION
import edu.nuwm.mentorbot.service.controls.keyboards.ButtonConstants.Companion.MENTOR_GET_MY_DIRECTIONS
import edu.nuwm.mentorbot.service.controls.keyboards.ButtonConstants.Companion.MENTOR_GET_OTHER_MENTORS
import edu.nuwm.mentorbot.service.handlers.MessageHandler.Companion.ERROR_GREETING
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage

@Component
class MentorMessageHandler(
        private val usersRepository: UsersRepository,
        private val directionsRepository: DirectionsRepository
) : AbstractMentorHandler(usersRepository) {

    override fun getMentorMessage(user: User, inputMessage: String): SendMessage {
        return when (inputMessage) {
            MENTOR_ADD_DIRECTION -> {
                user.state = State.MENTOR_ADD_DIRECTION
                usersRepository.save(user)
                SendMessage(user.chatId, ADDING_DIRECTION + "\n\n" + getDirections())
            }
            MENTOR_GET_MY_DIRECTIONS -> {
                SendMessage(user.chatId, getMentorDirections(user))
            }
            MENTOR_GET_OTHER_MENTORS -> {
                user.state = State.MENTOR_DEFAULT
                usersRepository.save(user)
                SendMessage(user.chatId, GETTING_OTHER_MENTORS)
            }
            else -> SendMessage(user.chatId, ERROR_GREETING)
        }
    }

    private fun getDirections(): String {
        return directionsRepository.findByActiveTrueOrderByName().withIndex()
                .joinToString("\n") { indexedValue -> "`${indexedValue.index + 1}. ${indexedValue.value.name}`" }
    }

    private fun getMentorDirections(user: User): String {
        val found = directionsRepository.findAllById(user.directions).toList()
        return if (found.isEmpty()) {
            "В тебе немає напрямів. Швидше додавай нові!"
        } else {
            found.joinToString("\n\n") { direction ->
                "`${direction.name}:` \n\n${direction.mentors[user.userId]}"
            }
        }
    }

    companion object {
        const val ADDING_DIRECTION = "Обери серед запропонованих напрямів або додай свій:"
        const val GETTING_OTHER_MENTORS = "Шукаємо інших менторів"
    }
}