package strava

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties

@SpringBootApplication
@EnableConfigurationProperties
class StravaApplication

fun main(args: Array<String>) {
    val springApplication = SpringApplication(StravaApplication::class.java)
    springApplication.setRegisterShutdownHook(true)

    springApplication.run()
}
