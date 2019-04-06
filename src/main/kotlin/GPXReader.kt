package main.kotlin

import com.google.gson.Gson
import org.apache.commons.io.IOUtils
import org.json.XML
import org.springframework.stereotype.Service
import org.springframework.util.ResourceUtils
import java.io.FileInputStream
import java.net.URL
import java.nio.charset.StandardCharsets


@Service
class GPXReader {

    companion object {
        const val JSON_INDENT_FACTOR = 4
    }

    fun readFileToDataObject(fileLocation: String, isURL: Boolean): GpxObject {
        return if (isURL) {
            createFromJSON(
                    getURL(fileLocation)
            )
        } else {
            createFromJSON(
                    getResource(fileLocation)
            )
        }
    }

    fun readFileToJson(fileLocation: String, isURL: Boolean): String {
        return if (isURL) {
            formatJson(getURL(fileLocation))
        } else {
            formatJson(getResource(fileLocation))
        }
    }
}

fun formatJson(json: String): String = XML.toJSONObject(json)
        .toString(GPXReader.JSON_INDENT_FACTOR).pruneJsonObject()

fun createFromJSON(json: String): GpxObject {
    var jsonObject = formatJson(json)

    return Gson()
            .fromJson(
                    jsonObject,
                    GpxObject::class.java
            )
}

fun String.pruneJsonObject(): String =
        this.replace("gpxtpx:TrackPointExtension", "trackpointextension")
                .replace("gpxtpx:hr", "hr")
                .replace("gpxtpx:", "")
                .replace("xmlns:", "")
                .replace("xsi:", "")

fun getResource(resource: String): String {
    val file = ResourceUtils.getFile(resource)
    val inputStream = FileInputStream(file)
    return IOUtils.toString(inputStream, StandardCharsets.UTF_8.name())
}

fun getURL(url: String): String {
    val file = URL(url).openStream()
    return IOUtils.toString(file, StandardCharsets.UTF_8.name())
}


