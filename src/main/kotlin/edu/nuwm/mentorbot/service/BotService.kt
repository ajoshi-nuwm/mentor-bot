package edu.nuwm.mentorbot.service

import edu.nuwm.mentorbot.persistence.UsersRepository
import edu.nuwm.mentorbot.persistence.entities.State
import edu.nuwm.mentorbot.persistence.entities.User
import edu.nuwm.mentorbot.service.controls.KeyboardProvider
import edu.nuwm.mentorbot.service.handlers.InitMessageHandler
import edu.nuwm.mentorbot.service.handlers.MentorAddDirectionHandler
import edu.nuwm.mentorbot.service.handlers.MentorMessageHandler
import edu.nuwm.mentorbot.service.handlers.SetTypeMessageHandler
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Message

@Service
class BotService(
        private val usersRepository: UsersRepository,
        private val initHandler: InitMessageHandler,
        private val setTypeHandler: SetTypeMessageHandler,
        private val mentorMessageHandler: MentorMessageHandler,
        private val mentorAddDirectionHandler: MentorAddDirectionHandler,
        private val keyboardProvider: KeyboardProvider
) {

    fun processMessage(message: Message): SendMessage {
        val chatId = message.chatId
        val userId = message.from.id
        val username = message.from.userName
        val inputMessage = message.text

        var user = usersRepository.findByIdOrNull(userId)
        if (user == null) {
            user = saveUser(User(userId, username, chatId, State.INIT))
        }

        val sendMessage = when (user.state) {
            State.INIT -> initHandler.getMessage(user)
            State.SET_TYPE -> setTypeHandler.getMessage(user, inputMessage)
            State.MENTOR_DEFAULT -> mentorMessageHandler.getMessage(user, inputMessage)
            State.MENTOR_ADD_DIRECTION -> mentorAddDirectionHandler.getMessage(user, inputMessage)
            else -> SendMessage(chatId, "Спробуй ще раз")
        }

        val currentState = getCurrentState(userId)
        addKeyboard(sendMessage, currentState)
        return sendMessage
    }

    private fun saveUser(user: User): User = usersRepository.save(user)

    private fun getCurrentState(userId: Int): State {
        return usersRepository.findById(userId).map { it.state }.orElse(State.INIT)
    }

    private fun addKeyboard(sendMessage: SendMessage, state: State) {
        sendMessage.replyMarkup = keyboardProvider.getKeyboard(state).getKeyboard()
    }
}