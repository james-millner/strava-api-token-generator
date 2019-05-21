package strava.athlete

import com.google.gson.Gson
import khttp.get
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import strava.config.StravaConfiguration
import strava.util.web.buildHeaders
import strava.util.web.getEndpointUrl

@RestController
class AthleteController(val config: StravaConfiguration, val gson: Gson) {

    @GetMapping(value = ["/athlete/stats"])
    fun getAthleteStats(@RequestParam("token") token: String) {

        val response = get(url = getEndpointUrl(config.stravaApiBaseUrl, "athlete/activities"),
                headers = buildHeaders(token))


    }


}
