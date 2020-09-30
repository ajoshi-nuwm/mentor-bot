package edu.nuwm.mentorbot.service.controls.keyboards

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow

object BackKeyboard : Keyboard {

    override fun getKeyboard(): ReplyKeyboard {
        val replyKeyboardMarkup = ReplyKeyboardMarkup()
        replyKeyboardMarkup.apply {
            selective = true
            resizeKeyboard = true
            oneTimeKeyboard = true
        }

        // Create keyboard rows
        val keyboard = ArrayList<KeyboardRow>()

        val keyboardRow = KeyboardRow()
        // Add buttons to row
        keyboardRow.add(KeyboardButton(ButtonConstants.BACK))

        // Add rows
        keyboard.add(keyboardRow)
        // Apply keyboard to message
        replyKeyboardMarkup.keyboard = keyboard
        return replyKeyboardMarkup
    }
}