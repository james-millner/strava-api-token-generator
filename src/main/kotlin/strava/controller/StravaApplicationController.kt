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
import strava.gpx.createGpxDataObjectFromXML
import java.nio.charset.StandardCharsets


@RestController
class StravaApplicationController(var gpxReader: GPXReader) {

    @PostMapping("/strava/file-upload")
    fun handleFileUpload(@RequestParam("file") file: MultipartFile,
                         @RequestParam("outputType") outputType: String,
                         redirectAttributes: RedirectAttributes): String {

        val xmlAsString = IOUtils.toString(file.inputStream, StandardCharsets.UTF_8.name())

        return when (outputType) {
            "json" -> gpxReader.readFileToJson(xmlAsString)
            "xml" -> xmlAsString
            "dataobject" -> createGpxDataObjectFromXML(xmlAsString).toString()
            else -> throw Exception("Unsupported output type. Please select from `json`, `xml`, `dataobject` (Produces kotlin data object.toString())" )
        }
    }

}
