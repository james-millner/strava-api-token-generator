package strava.gpx

import com.google.gson.Gson
import org.json.XML
import org.springframework.stereotype.Service

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
        .toString(GPXReader.JSON_INDENT_FACTOR)
        .pruneJsonObject()

fun createFromJSON(json: String): GpxObject = Gson()
        .fromJson(
                formatJson(json),
                GpxObject::class.java
        )

fun String.pruneJsonObject(): String =
        this.replace("gpxtpx:TrackPointExtension", "trackpointextension")
                .replace("gpxtpx:hr", "hr")
                .replace("gpxtpx:", "")
                .replace("xmlns:", "")
                .replace("xsi:", "")




