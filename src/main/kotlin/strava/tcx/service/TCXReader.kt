package strava.tcx.service

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service
import strava.tcx.models.TCXObject
import strava.util.modification.readFileToJson

@Service
class TCXReader(val objectMapper: ObjectMapper) {

    fun createGpxDataObjectFromJSON(xml: String): TCXObject = objectMapper
            .readValue(
                    readFileToJson(xml),
                    TCXObject::class.java
            )
}
