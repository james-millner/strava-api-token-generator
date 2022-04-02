package strava

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@SpringBootApplication
@EnableWebMvc
@EnableScheduling
@EnableConfigurationProperties
@OpenAPIDefinition
class StravaApplication

fun main(args: Array<String>) =
        SpringApplication(StravaApplication::class.java).bootStravaApplication()

fun SpringApplication.bootStravaApplication() {
    this.setRegisterShutdownHook(true)
    this.run()
}
