package edu.nuwm.mentorbot.persistence

import edu.nuwm.mentorbot.persistence.entities.UserState
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserStatesRepository : CrudRepository<UserState, Long>