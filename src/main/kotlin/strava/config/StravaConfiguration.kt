package strava.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.stereotype.Component
import strava.auth.GrantTypes

@Component
@ConfigurationProperties(prefix = "strava")
@EnableAsync
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

enum class StravaOAuthTokenType {
    CODE,
    REFRESH_TOKEN
}

fun StravaConfiguration.getMapOfStravaRequestParameters(
    grantType: GrantTypes,
    token: String,
    stravaOAuthTokenType: StravaOAuthTokenType
): MutableMap<String, String> {

    val clientId = clientId ?: throw Exception("Strava clientId not set correctly.")
    val clientSecret = clientSecret
            ?: throw Exception("Strava clientSecret not set correctly.")

    val parameters = mutableMapOf(
            "client_id" to clientId,
            "client_secret" to clientSecret
    )

    if (stravaOAuthTokenType.equals(StravaOAuthTokenType.CODE)) {
        parameters["code"] = token
        parameters["grant_type"] = grantType.name.lowercase()
    } else {
        parameters["refresh_token"] = token
        parameters["grant_type"] = grantType.name.lowercase()
    }

    return parameters
}
