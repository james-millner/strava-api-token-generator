package strava.tcx

import io.micrometer.core.annotation.Timed
import mu.KLogging
import org.apache.commons.io.IOUtils
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import strava.geocoding.GeocodeService
import strava.tcx.service.TCXReader
import strava.util.modification.readFileToJson
import java.nio.charset.StandardCharsets

@RestController
class TCXFileController(val tcxReader: TCXReader,
                        val geocodeService: GeocodeService,
                        val mongoTemplate: MongoTemplate) {

    companion object : KLogging()

    @Timed(histogram = true)
    @PostMapping("/strava/tcx/convert-file")
    fun handleFileConvert(
            @RequestParam("file") file: MultipartFile,
            @RequestParam("outputType") outputType: String
    ): String {

        val xmlAsString = IOUtils.toString(file.inputStream, StandardCharsets.UTF_8.name())

        return when (outputType.toLowerCase()) {
            "json" -> readFileToJson(xmlAsString)
            "xml" -> xmlAsString
            else -> throw Exception(
                    "Unsupported output type. Please select from `json`, `xml`")
        }
    }

    @Timed(histogram = true)
    @PostMapping("/strava/tcx/process-file")
    fun handleFileUpload(
            @RequestParam("file") file: MultipartFile
    ): String {

        val xmlAsString = IOUtils.toString(file.inputStream, StandardCharsets.UTF_8.name())
        val tcxObject = tcxReader.createGpxDataObjectFromJSON(xmlAsString)

        val geocodeResponses = geocodeService.getAddressInformationForTcxObject(tcxObject)

        geocodeResponses.forEach { result ->
            logger.info { result.formattedAddress + " - " + result.geometry }
            mongoTemplate.save(result)
        }


        return "OK"
    }
}
