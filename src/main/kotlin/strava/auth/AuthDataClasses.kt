package strava.auth

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class StravaToken(
        var tokenType: String,
        var expiresAt: Number,
        val expiresIn: Number,
        var refreshToken: String,
        @Id var accessToken: String
)

