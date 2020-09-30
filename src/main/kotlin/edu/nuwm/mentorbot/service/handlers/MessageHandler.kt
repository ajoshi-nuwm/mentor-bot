package edu.nuwm.mentorbot.service.handlers

import edu.nuwm.mentorbot.persistence.entities.User
import org.telegram.telegrambots.meta.api.methods.send.SendMessage

interface MessageHandler {
    fun getMessage(user: User, inputMessage: String = ""): SendMessage

    companion object {
        const val ERROR_GREETING = "Не зрозумів, давай ще раз"
    }
}