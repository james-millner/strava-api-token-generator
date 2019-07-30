package strava.gpx.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import org.springframework.stereotype.Service
import strava.gpx.models.GPXObject
import strava.util.modification.formatXMLToJson

@Service
class GPXReader(val objectMapper: ObjectMapper) {

    fun createGpxDataObjectFromJSON(xml: String): GPXObject = objectMapper
            .readValue(
                    formatXMLToJson(xml),
                    GPXObject::class.java
            )

    fun pruneGPXJson(json: String): String =
            json.replace("gpxtpx:TrackPointExtension", "trackpointextension")
                    .replace("gpxtpx:hr", "hr")
                    .replace("gpxtpx:atemp", "atemp")
                    .replace("gpxtpx:cad", "cad")
                    .replace("gpxtpx:", "")
                    .replace("xmlns:", "")
                    .replace("xsi:", "")
}


