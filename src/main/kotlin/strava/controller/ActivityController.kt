package strava.controller

import com.google.gson.Gson
import io.micrometer.core.annotation.Timed
import khttp.get
import mu.KLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import strava.athlete.ActivityService
import strava.athlete.AthleteActivity
import strava.config.StravaApplicationConfiguration
import strava.util.web.buildHeaders
import strava.util.web.getEndpointUrl
import strava.util.web.ifSuccessfulRequest

@RestController
class ActivityController(val config: StravaApplicationConfiguration, val activityService: ActivityService) {

    companion object : KLogging()

    @Timed(histogram = true)
    @GetMapping("/strava/activities")
    fun getActivities(@RequestParam("token") token: String,
                      @RequestParam("before") beforeDate: Long,
                      @RequestParam("after") afterDate: Long): List<AthleteActivity> {

        val response = get(url = getEndpointUrl(config.stravaApiBaseUrl, "athlete/activities"),
                headers = buildHeaders(token),
                params = mapOf(
                        "before" to beforeDate.toString(),
                        "after" to afterDate.toString()
                ))

        logger.debug { response.text }

        return if (ifSuccessfulRequest(response)) {

            val activitiesFound = Gson().fromJson(response.text, Array<AthleteActivity>::class.java)
                    .toList()

            activitiesFound.forEach {
                if (!activityService.activityRepo.existsById(it.upload_id!!)) {
                    activityService.save(it)
                }
            }

            activitiesFound
        } else {
            emptyList()
        }
    }

}

