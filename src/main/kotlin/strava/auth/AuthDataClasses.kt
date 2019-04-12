package strava.auth

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

data class Athlete(
        val id: Number?,
        val username: String?,
        val resource_state: Number?,
        val firstname: String?,
        val lastname: String?,
        val city: String?,
        val state: String?,
        val country: String?,
        val sex: String?,
        val premium: Boolean?,
        val summit: Boolean?,
        val created_at: String?,
        val updated_at: String?,
        val badge_type_id: Number?,
        val profile_medium: String?,
        val profile: String?,
        val friend: Any?,
        val follower: Any?)

@Document
data class RequestTokenResponse(
        val token_type: String?,
        val expires_at: Number?,
        val expires_in: Number?,
        val refresh_token: String?,
        @Id val access_token: String?,
        val athlete: Athlete?
)

data class RefreshTokenResponse(
        val access_token: String,
        val expires_at: Int,
        val expires_in: Int,
        val refresh_token: String,
        val token_type: String
)

