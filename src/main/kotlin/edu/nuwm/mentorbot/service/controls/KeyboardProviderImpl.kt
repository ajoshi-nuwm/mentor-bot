package edu.nuwm.mentorbot.service.controls

import edu.nuwm.mentorbot.persistence.entities.State
import edu.nuwm.mentorbot.service.controls.keyboards.*
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException

@Service
class KeyboardProviderImpl : KeyboardProvider {
    override fun getKeyboard(state: State): Keyboard {
        return when (state) {
            State.INIT -> EmptyKeyboard
            State.SET_TYPE -> SetTypeKeyboard
            State.MENTOR_DEFAULT -> MentorKeyboard
            State.MENTOR_ADD_DIRECTION -> BackKeyboard
            State.STUDENT_DEFAULT -> throw IllegalArgumentException("Not implemented yet")
            else -> EmptyKeyboard
        }
    }
}