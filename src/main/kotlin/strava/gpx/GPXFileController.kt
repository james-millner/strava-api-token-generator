package strava.gpx

import io.micrometer.core.annotation.Timed
import mu.KLogging
import org.apache.commons.io.IOUtils
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import strava.gpx.service.createGpxDataObjectFromJSON
import strava.gpx.service.readFileToJson
import java.nio.charset.StandardCharsets

@RestController
class GPXFileController {

    companion object : KLogging()

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
