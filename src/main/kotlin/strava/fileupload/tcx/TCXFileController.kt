package strava.fileupload.tcx

import io.micrometer.core.annotation.Timed
import java.nio.charset.StandardCharsets
import mu.KLogging
import org.apache.commons.io.IOUtils
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import strava.fileupload.tcx.service.TCXReader
import strava.util.modification.readFileToJson

@RestController
class TCXFileController(
    val tcxReader: TCXReader
) {

    companion object : KLogging()

    @Timed(histogram = true)
    @PostMapping("/strava/tcx/convert-file")
    fun handleFileConvert(
        @RequestParam("file") file: MultipartFile,
        @RequestParam("outputType") outputType: String
    ): String {

        val xmlAsString = IOUtils.toString(file.inputStream, StandardCharsets.UTF_8.name())

        return when (outputType.lowercase()) {
            "json" -> readFileToJson(xmlAsString)
            "xml" -> xmlAsString
            "dataobject" -> tcxReader.createTcxDataObjectFromJSON(xmlAsString).toString()
            else -> throw Exception(
                    "Unsupported output type. Please select from `json`, `xml`, `dataobject` (Produces kotlin data object.toString())")
        }
    }
}
