package strava

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.cache.annotation.EnableCaching
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties
class StravaApplication

fun main(args: Array<String>) =
        SpringApplication(StravaApplication::class.java).bootStravaApplication()

fun SpringApplication.bootStravaApplication() {
    this.setRegisterShutdownHook(true)
    this.run()
}
