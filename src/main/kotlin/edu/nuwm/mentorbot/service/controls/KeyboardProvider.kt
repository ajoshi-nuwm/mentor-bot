package edu.nuwm.mentorbot.service.controls

import edu.nuwm.mentorbot.persistence.entities.State
import edu.nuwm.mentorbot.service.controls.keyboards.Keyboard

interface KeyboardProvider {
    fun getKeyboard(state: State) : Keyboard
}