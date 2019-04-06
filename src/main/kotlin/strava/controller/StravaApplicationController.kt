package strava.controller

import org.apache.commons.io.IOUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import strava.gpx.GPXReader
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.PostMapping
import java.nio.charset.StandardCharsets


@RestController
class StravaApplicationController {

    @Autowired lateinit var gpxReader: GPXReader

    @GetMapping(value = ["/"])
    fun index(): String {
        return gpxReader.readFileToDataObject("classpath:strava-afternoon-ride.gpx", false).toString()
    }

    @GetMapping(value = ["/json"])
    fun getJson(): String {
        return gpxReader.readFileToJson("classpath:strava-afternoon-ride.gpx", false).toString()
    }

    @PostMapping("/strava/file-upload")
    fun handleFileUpload(@RequestParam("file") file: MultipartFile,
                         redirectAttributes: RedirectAttributes): String {

        val inputStream = file.inputStream
        val xmlAsString = IOUtils.toString(inputStream, StandardCharsets.UTF_8.name())

        return gpxReader.readFileToJson(xmlAsString)

    }

}
