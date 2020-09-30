package edu.nuwm.mentorbot.persistence

import edu.nuwm.mentorbot.persistence.entities.Direction
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface DirectionsRepository : CrudRepository<Direction, String> {
    fun findByActiveTrue(): Collection<Direction>
}