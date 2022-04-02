package strava.auth.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
@JsonIgnoreProperties(ignoreUnknown = true)
data class StravaToken(
    @JsonProperty("token_type")
    var tokenType: String,

    @JsonProperty("expires_at")
    var expiresAt: Long,

    @JsonProperty("expires_in")
    val expiresIn: Long,

    @JsonProperty("refresh_token")
    var refreshToken: String,

    @JsonProperty("access_token")
    @Id var accessToken: String
) {
    override fun equals(other: Any?): Boolean {
        if (other == null || other !is StravaToken) {
            return false
        }

        return refreshToken == other.refreshToken
    }

    override fun hashCode(): Int {
        return refreshToken.hashCode()
    }
}
