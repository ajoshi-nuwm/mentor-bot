package edu.nuwm.mentorbot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import org.telegram.telegrambots.ApiContextInitializer

@SpringBootApplication
class MentorBotApplication

fun main(args: Array<String>) {
    ApiContextInitializer.init()
    runApplication<MentorBotApplication>(*args)
}
