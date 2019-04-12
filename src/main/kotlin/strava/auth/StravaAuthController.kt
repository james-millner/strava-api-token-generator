package strava.auth

import com.google.gson.Gson
import khttp.post
import mu.KLogging
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import strava.config.StravaConfiguration
import strava.util.web.ifSuccessfulRequest

@Controller
class StravaAuthController(val stravaConfiguration: StravaConfiguration, val tokenService: TokenService) {

    companion object : KLogging()

    @GetMapping(value = ["/get-token"])
    fun getToken() = "redirect:" + buildTokenRefreshEndpoint(stravaConfiguration)

    @GetMapping(value = ["/auth-code"])
    @ResponseBody
    fun authCode(@RequestParam(name = "code") authCode: String): RequestTokenResponse? {

        val authUrl = stravaConfiguration.OAuthUrl ?: throw Exception("OAuth URL Properly not set correctly.")
        val response = post(url = authUrl, params = buildOAuthParams(stravaConfiguration, authCode))
        logger.info { response.statusCode }

        return if (ifSuccessfulRequest(response)) {

            val tokenResponse = Gson().fromJson(response.text, RequestTokenResponse::class.java)
            if(!tokenService.existsByToken(tokenResponse.access_token!!)) {
                tokenService.save(tokenResponse)
            }

            tokenResponse
        } else {
            null
        }
    }

    @GetMapping(value = ["/refresh-token"])
    @ResponseBody
    fun refreshToken(@RequestParam("token") refreshToken: String): RefreshTokenResponse? {

        val authUrl = stravaConfiguration.OAuthUrl ?: throw Exception("OAuth URL Properly not set correctly.")
        val response = post(url = authUrl, params = buildRefreshTokenParameters(stravaConfiguration, refreshToken))

        return if (ifSuccessfulRequest(response)) {

            //Add code to update token in DB.
            Gson().fromJson(response.text, RefreshTokenResponse::class.java)
        } else {
            null
        }
    }

}

fun buildOAuthParams(stravaConfiguration: StravaConfiguration, code: String): Map<String, String> {

    val clientId = stravaConfiguration.clientId ?: throw Exception("Strava clientId not set correctly.")
    val clientSecret = stravaConfiguration.clientSecret
            ?: throw Exception("Strava clientSecret not set correctly.")

    return mapOf(
            "client_id" to clientId,
            "client_secret" to clientSecret,
            "code" to code,
            "grant_type" to "authorization_code"
    )
}

fun buildRefreshTokenParameters(stravaConfiguration: StravaConfiguration, refreshToken: String): Map<String, String> {
    val clientId = stravaConfiguration.clientId ?: throw Exception("Strava clientId not set correctly.")
    val clientSecret = stravaConfiguration.clientSecret
            ?: throw Exception("Strava clientSecret not set correctly.")

    return mapOf(
            "client_id" to clientId,
            "client_secret" to clientSecret,
            "refresh_token" to refreshToken,
            "grant_type" to "refresh_token"
    )
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
