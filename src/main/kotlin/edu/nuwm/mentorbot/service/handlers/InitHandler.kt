package edu.nuwm.mentorbot.service.handlers

import edu.nuwm.mentorbot.persistence.UserStatesRepository
import edu.nuwm.mentorbot.persistence.entities.State
import edu.nuwm.mentorbot.persistence.entities.UserState
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow

@Component
class InitHandler(
        private val userStatesRepository: UserStatesRepository
) : Handler {

    override fun getMessage(chatId: Long, messageText: String): SendMessage {
        userStatesRepository.save(UserState(chatId, State.SET_TYPE))
        val response = SendMessage(chatId, "Привіт! Радий бачити тебе тут. Ти ментор чи студент?")
        setInitButtons(response)
        return response
    }

    override fun setInitButtons(sendMessage: SendMessage) {
        val replyKeyboardMarkup = ReplyKeyboardMarkup()
        sendMessage.replyMarkup = replyKeyboardMarkup
        replyKeyboardMarkup.selective = true
        replyKeyboardMarkup.resizeKeyboard = true
        replyKeyboardMarkup.oneTimeKeyboard = true

        // Create keyboard rows
        val keyboard = ArrayList<KeyboardRow>()

        val keyboardRow = KeyboardRow()
        // Add buttons to row
        keyboardRow.add(KeyboardButton("Ментор!"))
        keyboardRow.add(KeyboardButton("Студент!"))

        // Add rows
        keyboard.add(keyboardRow)
        // Apply keyboard to message
        replyKeyboardMarkup.keyboard = keyboard
    }
}