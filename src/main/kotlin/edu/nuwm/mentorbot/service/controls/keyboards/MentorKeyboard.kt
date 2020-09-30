package edu.nuwm.mentorbot.service.controls.keyboards

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow

object MentorKeyboard : Keyboard {

    override fun getKeyboard(): ReplyKeyboard {
        val replyKeyboardMarkup = ReplyKeyboardMarkup()
        replyKeyboardMarkup.apply {
            selective = true
            resizeKeyboard = true
            oneTimeKeyboard = true
        }

        // Create keyboard rows
        val keyboard = ArrayList<KeyboardRow>()

        val firstRow = KeyboardRow()
        // Add buttons to row
        firstRow.add(KeyboardButton(ButtonConstants.MENTOR_ADD_DIRECTION.toLowerCase().capitalize()))

        val secondRow = KeyboardRow()
        secondRow.add(KeyboardButton(ButtonConstants.MENTOR_GET_MY_DIRECTIONS.toLowerCase().capitalize()))
        secondRow.add(KeyboardButton(ButtonConstants.MENTOR_GET_OTHER_MENTORS.toLowerCase().capitalize()))
        // Add rows
        keyboard.add(firstRow)
        keyboard.add(secondRow)
        // Apply keyboard to message
        replyKeyboardMarkup.keyboard = keyboard
        return replyKeyboardMarkup
    }
}