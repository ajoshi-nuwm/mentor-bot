package edu.nuwm.mentorbot.persistence.entities

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "directions")
data class Direction(
        @Id val name: String,
        // TODO: set to false
        val active: Boolean = true,
        val mentors: MutableMap<Int, String> = mutableMapOf()
)