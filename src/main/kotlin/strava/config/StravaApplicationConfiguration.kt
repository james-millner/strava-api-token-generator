package strava.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "strava")
@EnableConfigurationProperties
data class StravaApplicationConfiguration(
        var accessToken: String? = null,
        var clientId: String? = null,
        var clientSecret: String? = null,
        var authorizationCode: String? = null,
        var url: String? = null,
        var OAuthUrl: String? = null,
        val stravaApiBaseUrl: String = "https://www.strava.com/api/v3/"
)
