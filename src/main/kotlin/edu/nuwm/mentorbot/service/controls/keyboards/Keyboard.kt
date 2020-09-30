package edu.nuwm.mentorbot.service.controls.keyboards

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard

interface Keyboard {
    fun getKeyboard(): ReplyKeyboard
}