package edu.nuwm.mentorbot.persistence.entities

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "directions")
data class Direction(
        @Id val name: String,
        val active: Boolean = false,
        val mentors: MutableMap<Int, String> = mutableMapOf()
)