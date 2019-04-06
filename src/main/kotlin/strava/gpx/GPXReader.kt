package strava.gpx

import com.google.gson.Gson
import org.json.XML
import org.springframework.stereotype.Service

@Service
class GPXReader {

    companion object {
        const val JSON_INDENT_FACTOR = 4
    }

    fun readFileToJson(fileAsString: String): String = formatXMLToJson(fileAsString)
}

fun formatXMLToJson(xml: String): String = XML.toJSONObject(xml)
        .toString(GPXReader.JSON_INDENT_FACTOR)
        .pruneJsonObject()

fun createGpxDataObjectFromXML(xml: String): GpxObject = Gson()
        .fromJson(
                formatXMLToJson(xml),
                GpxObject::class.java
        )

fun String.pruneJsonObject(): String =
        this.replace("gpxtpx:TrackPointExtension", "trackpointextension")
                .replace("gpxtpx:hr", "hr")
                .replace("gpxtpx:", "")
                .replace("xmlns:", "")
                .replace("xsi:", "")




