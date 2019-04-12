package strava.auth

import com.google.gson.Gson
import khttp.post
import mu.KLogging
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import strava.config.StravaConfiguration
import strava.config.getMapOfClientSecrets
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

        val parameters = stravaConfiguration.getMapOfClientSecrets()
        parameters["code"] = authCode
        parameters["grant_type"] = "authorization_code"

        val response = post(url = authUrl, params = parameters)
        logger.info { "Get authentication token: $response.statusCode" }

        return if (ifSuccessfulRequest(response)) {

            val tokenResponse = Gson().fromJson(response.text, RequestTokenResponse::class.java)
            if (!tokenService.existsByToken(tokenResponse.access_token!!)) {
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

        val parameters = stravaConfiguration.getMapOfClientSecrets()
        parameters["refresh_token"] = refreshToken
        parameters["grant_type"] = "refresh_token"

        val response = post(url = authUrl, params = parameters)
        logger.info { "Refresh token: $response.statusCode" }
        return if (ifSuccessfulRequest(response)) {

            //Add code to update token in DB.
            Gson().fromJson(response.text, RefreshTokenResponse::class.java)
        } else {
            null
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
