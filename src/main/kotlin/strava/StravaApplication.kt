package strava

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties

@SpringBootApplication
@EnableConfigurationProperties
class StravaApplication

fun main(args: Array<String>) =
        SpringApplication(StravaApplication::class.java).bootStravaApplication()

fun SpringApplication.bootStravaApplication() {
    this.setRegisterShutdownHook(true)
    this.run()
}
