package edu.nuwm.mentorbot.service.controls.keyboards

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove

object EmptyKeyboard : Keyboard {
    override fun getKeyboard(): ReplyKeyboard = ReplyKeyboardRemove()
}