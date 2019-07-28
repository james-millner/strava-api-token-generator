package strava.gpx.service

import com.google.gson.Gson
import org.json.XML
import strava.gpx.models.GpxObject

fun readFileToJson(fileAsString: String): String = formatXMLToJson(fileAsString)

fun formatXMLToJson(xml: String): String = XML.toJSONObject(xml)
        .toString()
        .pruneJsonObject()

fun createGpxDataObjectFromJSON(xml: String): GpxObject = Gson()
        .fromJson(
                formatXMLToJson(xml),
                GpxObject::class.java
        )

fun String.pruneJsonObject(): String =
        this.replace("gpxtpx:TrackPointExtension", "trackpointextension")
                .replace("gpxtpx:hr", "hr")
                .replace("gpxtpx:atemp", "atemp")
                .replace("gpxtpx:cad", "cad")
                .replace("gpxtpx:", "")
                .replace("xmlns:", "")
                .replace("xsi:", "")




