package strava.tpx.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import org.springframework.stereotype.Service
import strava.tpx.models.TCXObject
import strava.util.modification.formatXMLToJson

@Service
class TCXReader(val objectMapper: ObjectMapper) {

    fun createGpxDataObjectFromJSON(xml: String): TCXObject = objectMapper
            .readValue(
                    formatXMLToJson(xml),
                    TCXObject::class.java
            )
}
