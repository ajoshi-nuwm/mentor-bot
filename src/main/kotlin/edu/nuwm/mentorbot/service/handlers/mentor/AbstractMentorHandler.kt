package edu.nuwm.mentorbot.service.handlers.mentor

import edu.nuwm.mentorbot.persistence.UsersRepository
import edu.nuwm.mentorbot.persistence.entities.State
import edu.nuwm.mentorbot.persistence.entities.User
import edu.nuwm.mentorbot.service.controls.keyboards.ButtonConstants
import edu.nuwm.mentorbot.service.handlers.MessageHandler
import org.telegram.telegrambots.meta.api.methods.ParseMode
import org.telegram.telegrambots.meta.api.methods.send.SendMessage

abstract class AbstractMentorHandler(
        private val usersRepository: UsersRepository
) : MessageHandler {

    override fun getMessage(user: User, inputMessage: String): SendMessage {
        if (inputMessage == ButtonConstants.BACK) {
            user.state = State.MENTOR_DEFAULT
            usersRepository.save(user)
            return SendMessage(user.chatId, MentorAddDirectionHandler.NEXT_TIME)
        }
        return try {
            getMentorMessage(user, inputMessage).setParseMode(ParseMode.MARKDOWN)
        } catch (e: Exception) {
            user.state = State.MENTOR_DEFAULT
            usersRepository.save(user)
            SendMessage(user.chatId, MentorAddDirectionHandler.ERROR)
        }
    }

    abstract fun getMentorMessage(user: User, inputMessage: String): SendMessage
}