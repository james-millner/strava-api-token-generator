package strava.auth

import com.fasterxml.jackson.databind.ObjectMapper
import mu.KLogging
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.view.RedirectView
import strava.auth.models.FailedStravaToken
import strava.auth.models.StravaTokenResponse
import strava.auth.models.SuccessfulStravaToken
import strava.config.StravaConfiguration
import strava.config.StravaOAuthTokenType
import strava.config.getMapOfStravaRequestParameters
import strava.util.web.toFormBody

enum class GrantTypes {
    AUTHORIZATION_CODE,
    REFRESH_TOKEN
}

@RestController
class StravaAuthController(
    val stravaConfiguration: StravaConfiguration,
    val objectMapper: ObjectMapper
) {

    companion object : KLogging()

    @GetMapping(value = ["/"], produces=["application/json"])
    fun getToken(): RedirectView = RedirectView(
        buildTokenRefreshEndpoint(stravaConfiguration)
    )

    @GetMapping(value = ["/auth-code"])
    @ResponseBody
    fun authCode(@RequestParam(name = "code") authCode: String): ResponseEntity<StravaTokenResponse> {

        val authUrl = stravaConfiguration.OAuthUrl ?: throw Exception("OAuth URL Properly not set correctly.")

        val body = stravaConfiguration.getMapOfStravaRequestParameters(
                GrantTypes.AUTHORIZATION_CODE,
                authCode,
                StravaOAuthTokenType.CODE
        ).toFormBody()

        val response = OkHttpClient().newCall(
            request = Request.Builder()
                        .url(authUrl)
                        .post(body)
                        .build()
        ).execute()

        logger.debug { "Get authentication token: ${response.code}, ${response.message}" }

        return if (response.isSuccessful) {
            logger.info { "Token generated." }
            val stravaToken = objectMapper.readValue(response.body?.string(), SuccessfulStravaToken::class.java)
                    ?: throw Exception("Error with token...")

            ResponseEntity.ok().body(stravaToken)
        } else {
            ResponseEntity
                .badRequest()
                .body(
                    createStravaTokenErrorMessage(response)
                )
        }
    }

    @GetMapping(value = ["/refresh-token"])
    @ResponseBody
    fun refreshToken(@RequestParam("token") refreshToken: String): ResponseEntity<StravaTokenResponse> {

        val authUrl = stravaConfiguration.OAuthUrl ?: throw Exception("OAuth URL Properly not set correctly.")

        val body = stravaConfiguration.getMapOfStravaRequestParameters(
                GrantTypes.REFRESH_TOKEN,
                refreshToken,
                StravaOAuthTokenType.REFRESH_TOKEN
        ).toFormBody()

        val response = OkHttpClient().newCall(
                request = Request.Builder()
                        .url(authUrl)
                        .post(body)
                        .build()
        ).execute()

        logger.debug { "Refresh token: ${response.code}, ${response.message}" }
        return if (response.isSuccessful) {

            val stravaTokenResponse = objectMapper.readValue(response.body?.string(), SuccessfulStravaToken::class.java)
            ResponseEntity.ok().body(stravaTokenResponse)
        } else {
            ResponseEntity
                .badRequest()
                .body(
                    createStravaTokenErrorMessage(response)
                )
        }
    }
}

fun createStravaTokenErrorMessage(response: Response) =
    when(response.code) {
        401 -> {
            FailedStravaToken(
                statusCode = response.code,
                message = "Unauthorized, please check your deployment configuration for your Strava application."
            )
        }
        else -> {
            FailedStravaToken(
                statusCode = response.code,
                message = "Unknown error. ${response.code} received from Strava. ${response.request}"
            )
        }
    }

fun buildTokenRefreshEndpoint(stravaConfiguration: StravaConfiguration): String {
    return StringBuilder().append(stravaConfiguration.url)
            .append("?client_id=" + stravaConfiguration.clientId)
            .append("&redirect_uri=http://localhost:8080/auth-code")
            .append("&response_type=code")
            .append("&approval_prompt=force")
            .append("&scope=read,activity:read_all,profile:read_all,read_all")
            .toString()
}
