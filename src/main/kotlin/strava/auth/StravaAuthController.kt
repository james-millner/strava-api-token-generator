package strava.auth

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
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

    companion object : KLogging() {
        enum class GrantTypes {
            AUTHORIZATION_CODE,
            REFRESH_TOKEN
        }
    }

    private val gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()

    @GetMapping(value = ["/get-token"])
    fun getToken() = "redirect:" + buildTokenRefreshEndpoint(stravaConfiguration)

    @GetMapping(value = ["/auth-code"])
    @ResponseBody
    fun authCode(@RequestParam(name = "code") authCode: String): StravaToken? {

        val authUrl = stravaConfiguration.OAuthUrl ?: throw Exception("OAuth URL Properly not set correctly.")

        val parameters = stravaConfiguration.getMapOfClientSecrets()
        parameters["code"] = authCode
        parameters["grant_type"] = GrantTypes.AUTHORIZATION_CODE.name.toLowerCase()

        val response = post(url = authUrl, params = parameters)
        logger.info { "Get authentication token: $response.statusCode" }

        return if (ifSuccessfulRequest(response)) {

            val stravaToken = gson.fromJson(response.text, StravaToken::class.java)

            if (!tokenService.existsByToken(stravaToken.accessToken)) {
                tokenService.save(stravaToken)
            }

            stravaToken
        } else {
            null
        }
    }

    @GetMapping(value = ["/refresh-token"])
    @ResponseBody
    fun refreshToken(@RequestParam("token") refreshToken: String): StravaToken? {

        val authUrl = stravaConfiguration.OAuthUrl ?: throw Exception("OAuth URL Properly not set correctly.")

        val parameters = stravaConfiguration.getMapOfClientSecrets()
        parameters["refresh_token"] = refreshToken
        parameters["grant_type"] = GrantTypes.REFRESH_TOKEN.name.toLowerCase()

        val response = post(url = authUrl, params = parameters)
        logger.info { "Refresh token: $response.statusCode" }
        return if (ifSuccessfulRequest(response)) {

            val stravaTokenResponse = gson.fromJson(response.text, StravaToken::class.java)
            //Add code to update token in DB.
            val existingToken = tokenService.findByRefreshToken(stravaTokenResponse.refreshToken)
                    ?: throw Exception("Token doesn't exist")

            existingToken.accessToken = stravaTokenResponse.accessToken
            existingToken.refreshToken = stravaTokenResponse.refreshToken

            tokenService.save(existingToken)
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
