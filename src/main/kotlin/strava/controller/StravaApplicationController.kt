package strava.controller

import com.google.gson.Gson
import io.micrometer.core.annotation.Timed
import khttp.get
import mu.KLogging
import org.apache.commons.io.IOUtils
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import strava.athlete.ActivityService
import strava.athlete.AthleteActivity
import strava.config.StravaApplicationConfiguration
import strava.gpx.createGpxDataObjectFromJSON
import strava.gpx.readFileToJson
import java.nio.charset.StandardCharsets


@RestController
class StravaApplicationController(val config: StravaApplicationConfiguration, val activityService: ActivityService) {

    companion object : KLogging()

    @Timed(histogram = true)
    @GetMapping("/strava/activities")
    fun getActivities(@RequestParam("token") token: String,
                      @RequestParam("before") beforeDate: Long,
                      @RequestParam("after") afterDate: Long): List<AthleteActivity> {

        logger.info { beforeDate }
        logger.info { afterDate }

        val response = get(url = getEndpointUrl(config.stravaApiBaseUrl, "athlete/activities"),
                headers = buildHeaders(token),
                params = mapOf(
                        "before" to beforeDate.toString(),
                        "after" to afterDate.toString()
                ))

        println(response.text)

        val activitiesFound = Gson().fromJson(response.text, Array<AthleteActivity>::class.java).toList()

        activitiesFound.forEach {
            if(!activityService.activityRepo.existsById(it.upload_id!!)) {
                activityService.activityRepo.save(it)
            }
        }

        return if (response.statusCode == 200) {
            activitiesFound
        } else {
            emptyList()
        }
    }

    @Timed(histogram = true)
    @PostMapping("/strava/file-upload")
    fun handleFileUpload(@RequestParam("file") file: MultipartFile,
                         @RequestParam("outputType") outputType: String): String {

        val xmlAsString = IOUtils.toString(file.inputStream, StandardCharsets.UTF_8.name())

        return when (outputType.toLowerCase()) {
            "json" -> readFileToJson(xmlAsString)
            "xml" -> xmlAsString
            "dataobject" -> createGpxDataObjectFromJSON(xmlAsString).toString()
            else -> throw Exception("Unsupported output type. Please select from `json`, `xml`, `dataobject` (Produces kotlin data object.toString())")
        }
    }
}

fun buildHeaders(token: String): Map<String, String> =
        mapOf("Authorization" to "Bearer $token")

fun getEndpointUrl(url: String, endpoint: String) =
        StringBuilder()
                .append(url)
                .append(endpoint)
                .toString()
