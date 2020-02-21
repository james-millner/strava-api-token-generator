package strava.activities

import com.google.gson.Gson
import io.micrometer.core.annotation.Timed
import khttp.get
import mu.KLogging
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import strava.activities.models.ActivityResponse
import strava.activities.service.ActivityService
import strava.athlete.model.AthleteActivity
import strava.athlete.service.AthleteActivityService
import strava.config.StravaConfiguration
import strava.util.web.buildHeaders
import strava.util.web.getEndpointUrl
import strava.util.web.ifSuccessfulRequest
import java.time.LocalDateTime
import kotlin.math.log

@RestController
class ActivityController(val config: StravaConfiguration, val gson: Gson, val athleteActivityService: AthleteActivityService, val activityService: ActivityService) {

    companion object : KLogging()

    @Timed(histogram = true)
    @GetMapping("/strava/activities")
    fun getAthleteActivities(@RequestParam("token") token: String,
                             @RequestParam("before") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) beforeDateTime: LocalDateTime,
                             @RequestParam("after") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) afterDateTime: LocalDateTime): List<AthleteActivity> {

        val response = get(url = getEndpointUrl(config.stravaApiBaseUrl, "athlete/activities"),
                headers = buildHeaders(token),
                params = mapOf(
                        "before" to beforeDateTime.toEpochSecond(config.zoneOffset).toString(),
                        "after" to afterDateTime.toEpochSecond(config.zoneOffset).toString()
                ))

        logger.debug { response.text }

        return if (ifSuccessfulRequest(response)) {

            logger.info { "Successful request for activities between $afterDateTime - $beforeDateTime" }

            val activitiesFound = gson.fromJson(response.text, Array<AthleteActivity>::class.java)
                    .toList()

            activitiesFound.forEach {
                if (!athleteActivityService.existsById(it.upload_id!!)) {
                    athleteActivityService.save(it)
                    getActivity(it.id, token)
                }
            }

            activitiesFound
        } else {
            emptyList()
        }
    }

    @Timed(histogram = true)
    @GetMapping("/strava/activity/{id}")
    fun getActivity(@PathVariable("id") activityId: Number?,
                    @RequestParam("token") token: String): ActivityResponse? {

        val response = get(url = getEndpointUrl(config.stravaApiBaseUrl, "activities/$activityId"),
                headers = buildHeaders(token)
        )

        return if (ifSuccessfulRequest(response)) {

            logger.info { "Successfully requested activity" }

            val activityResponse = gson.fromJson(response.text, ActivityResponse::class.java)

            logger.debug { "${activityResponse.externalId} activity saved." }

            when (activityService.existsById(activityResponse.id!!)) {
                true -> activityResponse
                false -> activityService.save(activityResponse)
            }
        } else {
            logger.debug { "$activityId activity request unsuccessful: $response" }
            null
        }
    }

}
