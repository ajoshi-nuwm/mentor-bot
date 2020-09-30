package edu.nuwm.mentorbot.persistence

import edu.nuwm.mentorbot.persistence.entities.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UsersRepository : CrudRepository<User, Int>