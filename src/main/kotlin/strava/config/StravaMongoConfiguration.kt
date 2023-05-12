package strava.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class StravaMongoConfiguration(
    @Value("\${spring.data.mongodb.host}") val mongoHost: String,
    @Value("\${spring.data.mongodb.port}") val mongoPort: Int
) {

}
