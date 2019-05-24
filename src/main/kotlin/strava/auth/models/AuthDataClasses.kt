package strava.auth.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class StravaToken(
        var tokenType: String,
        var expiresAt: Number,
        val expiresIn: Number,
        var refreshToken: String,
        @Id var accessToken: String
) {
        override fun equals(other: Any?): Boolean {
                if(other == null || other !is StravaToken) {
                        return false
                }

                return refreshToken == other.refreshToken
        }

        override fun hashCode(): Int {
                return refreshToken.hashCode()
        }
}

