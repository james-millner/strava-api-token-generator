package strava.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import strava.gpx.GPXReader

@RestController
class StravaApplicationController {

    @Autowired val gpxReader: GPXReader? = null

    @GetMapping(value = ["/"])
    fun index(): String {
        return gpxReader?.readFileToDataObject("classpath:strava-afternoon-ride.gpx", false).toString()
    }

    @GetMapping(value = ["/json"])
    fun getJson(): String {
        return gpxReader?.readFileToJson("classpath:strava-afternoon-ride.gpx", false).toString()
    }

}
