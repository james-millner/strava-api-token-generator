package main.kotlin

import com.google.gson.Gson
import org.apache.commons.io.IOUtils
import org.json.XML
import org.springframework.stereotype.Service
import org.springframework.util.ResourceUtils
import java.io.FileInputStream
import java.net.URL
import java.nio.charset.StandardCharsets
import javax.annotation.PostConstruct


@Service
class GPXReader {

    companion object {
        const val PRETTY_PRINT_INDENT_FACTOR = 4
    }

    @PostConstruct
    fun init() {

        val gpxObject = readFile("classpath:strava-afternoon-ride.gpx", false)

        println(gpxObject.toString())

    }

    fun readFile(fileLocation: String, isURL: Boolean): GpxObject {
        return if(isURL) {
            createFromJSON(
                    getURL(fileLocation)
            )
        } else {
            createFromJSON(
                    getResource(fileLocation)
            )
        }

    }
}

fun createFromJSON(json: String): GpxObject {
    var jsonObject = XML.toJSONObject(json)
            .toString(GPXReader.PRETTY_PRINT_INDENT_FACTOR)

    return Gson()
            .fromJson(
                    jsonObject.pruneJsonObject(),
                    GpxObject::class.java
            )
}

fun String.pruneJsonObject(): String =
        this.replace("gpxtpx:", "")
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


