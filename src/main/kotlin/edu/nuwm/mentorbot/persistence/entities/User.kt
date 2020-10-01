package edu.nuwm.mentorbot.persistence.entities

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "users")
data class User(
        @Id val userId: Int,
        val username: String,
        val chatId: Long,
        var state: State,
        val context: MutableMap<String, String> = mutableMapOf(),
        val directions: MutableSet<String> = mutableSetOf()
)

enum class State {
    // initial states
    INIT,
    SET_TYPE,

    // mentor states
    MENTOR_DEFAULT,
    MENTOR_ADD_DIRECTION,
    MENTOR_ADD_EXPERIENCE,

    // student states
    STUDENT_DEFAULT
}