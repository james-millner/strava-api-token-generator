package strava.auth

import com.fasterxml.jackson.databind.ObjectMapper
import khttp.post
import mu.KLogging
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import strava.auth.models.StravaToken
import strava.auth.service.TokenService
import strava.config.StravaConfiguration
import strava.config.StravaOAuthTokenType
import strava.config.getMapOfStravaRequestParameters
import strava.util.web.ifSuccessfulRequest

enum class GrantTypes {
    AUTHORIZATION_CODE,
    REFRESH_TOKEN
}

@Controller
class StravaAuthController(
        val stravaConfiguration: StravaConfiguration,
        val tokenService: TokenService,
        val objectMapper: ObjectMapper
) {

    val cache = stravaConfiguration.tokenCache

    companion object : KLogging()

    @GetMapping(value = ["/get-token"])
    fun getToken() = "redirect:" + buildTokenRefreshEndpoint(stravaConfiguration)

    @GetMapping(value = ["/auth-code"])
    @ResponseBody
    fun authCode(@RequestParam(name = "code") authCode: String): ResponseEntity<StravaToken> {

        val authUrl = stravaConfiguration.OAuthUrl ?: throw Exception("OAuth URL Properly not set correctly.")

        val parameters = stravaConfiguration.getMapOfStravaRequestParameters(
                GrantTypes.AUTHORIZATION_CODE,
                authCode,
                StravaOAuthTokenType.CODE
        )

        val response = post(url = authUrl, params = parameters)
        logger.info { "Get authentication token: $response.statusCode" }

        return if (ifSuccessfulRequest(response)) {

            val stravaToken = objectMapper.readValue(response.text, StravaToken::class.java)
                    ?: throw Exception("Error with token...")

            cache.cacheStravaToken(stravaToken)

            val token = when (tokenService.existsByRefreshToken(stravaToken.refreshToken)) {
                true -> cache.getStravaToken(stravaToken)
                false -> cache.getStravaToken(tokenService.save(stravaToken))
            } ?: throw Exception("Unable to get Strava Token")

            ResponseEntity.ok().body(token)
        } else {
            ResponseEntity.badRequest().build()
        }
    }

    @GetMapping(value = ["/refresh-token"])
    @ResponseBody
    fun refreshToken(@RequestParam("token") refreshToken: String): Any? {

        val authUrl = stravaConfiguration.OAuthUrl ?: throw Exception("OAuth URL Properly not set correctly.")

        val parameters = stravaConfiguration.getMapOfStravaRequestParameters(
                GrantTypes.REFRESH_TOKEN,
                refreshToken,
                StravaOAuthTokenType.REFRESH_TOKEN
        )

        val response = post(url = authUrl, params = parameters)
        logger.info { "Refresh token: $response.statusCode" }
        return if (ifSuccessfulRequest(response)) {

            val stravaTokenResponse = objectMapper.readValue(response.text, StravaToken::class.java)

            tokenService.deleteByRefreshToken(stravaTokenResponse.refreshToken)

            val persistedToken = tokenService.save(stravaTokenResponse)

            cache.cacheStravaToken(persistedToken)
                    .run { cache.getStravaToken(persistedToken) }

        } else {
            response
        }
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
