package strava.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
@ConfigurationProperties(prefix = "strava")
@EnableConfigurationProperties
data class StravaConfiguration(
        var accessToken: String? = null,
        var clientId: String? = null,
        var clientSecret: String? = null,
        var authorizationCode: String? = null,
        var url: String? = null,
        var OAuthUrl: String? = null,
        val stravaApiBaseUrl: String = "https://www.strava.com/api/v3/"
)

fun StravaConfiguration.getMapOfClientSecrets(): MutableMap<String, String> {

    val clientId = clientId ?: throw Exception("Strava clientId not set correctly.")
    val clientSecret = clientSecret
            ?: throw Exception("Strava clientSecret not set correctly.")

    return mutableMapOf("client_id" to clientId,
            "client_secret" to clientSecret)
}

@Configuration
class StravaApplicationConfiguration() {

    @Bean
    fun restTemplate(): RestTemplate = RestTemplate()
}
