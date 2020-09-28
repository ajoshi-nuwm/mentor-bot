package edu.nuwm.mentorbot.persistence.entities

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "user_states")
data class UserState(
        @Id val chatId: Long,
        val state: State
)

enum class State {
    INIT,
    SET_TYPE,
    MENTOR_DEFAULT,
    STUDENT_DEFAULT
}