package main.kotlin

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class StravaApplication

fun main(args: Array<String>) {
    val springApplication = SpringApplication(StravaApplication::class.java)
    springApplication.setRegisterShutdownHook(true)

    springApplication.run()
}
