package strava.fileupload.tcx.service

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service
import strava.fileupload.tcx.models.TCXObject
import strava.util.modification.readFileToJson

@Service
class TCXReader(val objectMapper: ObjectMapper) {

    fun createTcxDataObjectFromJSON(xml: String): TCXObject = objectMapper
            .readValue(
                    readFileToJson(xml),
                    TCXObject::class.java
            )
}
