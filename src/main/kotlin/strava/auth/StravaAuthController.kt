package strava.auth

import com.google.gson.Gson
import khttp.post
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class StravaAuthController(val stravaApplicationConfiguration: StravaApplicationConfiguration) {

    @GetMapping(value = ["/refresh-token"])
    fun refreshToken() = "redirect:" + buildTokenRefreshEndpoint(stravaApplicationConfiguration)

    @GetMapping(value = ["/auth-code"])
    @ResponseBody
    fun authCode(@RequestParam(name = "code") authCode: String): RefreshTokenResponse {

        val authUrl = stravaApplicationConfiguration.OAuthUrl ?: throw Exception("OAuth URL Propert not set correctly.")
        val response = post(url = authUrl, params = buildOAuthParams(stravaApplicationConfiguration, authCode))

        return Gson().fromJson(response.text, RefreshTokenResponse::class.java)
    }

}

fun buildOAuthParams(stravaApplicationConfiguration: StravaApplicationConfiguration, code: String): Map<String, String> {

    val clientId = stravaApplicationConfiguration.clientId ?: throw Exception("Strava clientId not set correctly.")
    val clientSecret = stravaApplicationConfiguration.clientSecret
            ?: throw Exception("Strava clientSecret not set correctly.")

    return mapOf(
            "client_id" to clientId,
            "client_secret" to clientSecret,
            "code" to code,
            "grant_type" to "authorization_code"
    )
}

fun buildTokenRefreshEndpoint(stravaApplicationConfiguration: StravaApplicationConfiguration): String {
    return StringBuilder().append(stravaApplicationConfiguration.url)
            .append("?client_id=" + stravaApplicationConfiguration.clientId)
            .append("&redirect_uri=http://localhost:8080/auth-code")
            .append("&response_type=code")
            .append("&approval_prompt=force")
            .append("&scope=read,activity:read_all,profile:read_all,read_all")
            .toString()
}
