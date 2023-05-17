package strava.auth.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class SuccessfulStravaToken(
    @JsonProperty("token_type")
    var tokenType: String,

    @JsonProperty("expires_at")
    var expiresAt: Long,

    @JsonProperty("expires_in")
    val expiresIn: Long,

    @JsonProperty("refresh_token")
    var refreshToken: String,

    @JsonProperty("access_token")
    var accessToken: String
): StravaTokenResponse() {
    override fun equals(other: Any?): Boolean {
        if (other == null || other !is SuccessfulStravaToken) {
            return false
        }

        return refreshToken == other.refreshToken
    }

    override fun hashCode(): Int {
        return refreshToken.hashCode()
    }
}

data class FailedStravaToken(
    @JsonProperty("status_code")
    val statusCode: Int,
    val message: String
): StravaTokenResponse()

sealed class StravaTokenResponse
