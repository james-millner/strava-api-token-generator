package strava.activities

import com.google.gson.Gson
import io.micrometer.core.annotation.Timed
import khttp.get
import mu.KLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import strava.activities.models.ActivityResponse
import strava.activities.service.ActivityService
import strava.athlete.AtheleteActivityService
import strava.athlete.AthleteActivity
import strava.config.StravaConfiguration
import strava.util.web.buildHeaders
import strava.util.web.getEndpointUrl
import strava.util.web.ifSuccessfulRequest

@RestController
class ActivityController(val config: StravaConfiguration, val gson: Gson, val atheleteActivityService: AtheleteActivityService, val activityService: ActivityService) {

    companion object : KLogging()

    @Timed(histogram = true)
    @GetMapping("/strava/activities")
    fun getAthleteActivities(@RequestParam("token") token: String,
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

            val activitiesFound = gson.fromJson(response.text, Array<AthleteActivity>::class.java)
                    .toList()

            activitiesFound.forEach {
                if (!atheleteActivityService.activityRepo.existsById(it.upload_id!!)) {
                    atheleteActivityService.save(it)
                }
            }

            activitiesFound
        } else {
            emptyList()
        }
    }

    @GetMapping("/strava/activity/{id}")
    fun getActivity(@PathVariable("id") activityId: Long,
                    @RequestParam("token") token: String): ActivityResponse? {

        val response = get(url = getEndpointUrl(config.stravaApiBaseUrl, "activities/$activityId"),
                headers = buildHeaders(token)
        )

        return if (ifSuccessfulRequest(response)) {
            val activityResponse = gson.fromJson(response.text, ActivityResponse::class.java)

            when (activityService.existsById(activityResponse.id!!)) {
                true -> activityResponse
                false -> activityService.save(activityResponse)
            }
        } else {
            null
        }
    }

}
