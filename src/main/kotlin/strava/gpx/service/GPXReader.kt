package strava.gpx.service

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service
import strava.gpx.models.GPXObject
import strava.util.modification.readFileToJson

@Service
class GPXReader(val objectMapper: ObjectMapper) {

    fun createGpxDataObjectFromJSON(xml: String): GPXObject = objectMapper
            .readValue(
                    readFileToJson(xml),
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
