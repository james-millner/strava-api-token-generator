package strava.tpx.service

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import junit.framework.Assert.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import strava.getResource
import strava.util.modification.formatXMLToJson
import java.time.LocalDateTime

internal class TXCReaderTest {

    @Nested
    inner class `When the TCX reader receives XML as a String` {

        private val objectMapper = ObjectMapper()
                .registerModule(JavaTimeModule())
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

        private val tcxReader = TCXReader(objectMapper)

        private val RAW_TCX_DATA = getResource("classpath:tcx/First_little_ride_with_Wahoo_.tcx")

        @Test
        fun `the XML is successfully parsed and converted to a GPXObject`() {
            println(formatXMLToJson(RAW_TCX_DATA))

            val obj = tcxReader.createGpxDataObjectFromJSON(RAW_TCX_DATA)
            assertAll("The objects values are correct",
                      { assertEquals("Biking", obj.trainingCenterDatabase?.activities?.activity?.sport) },
                      { assertEquals("2019-07-28T13:16:43Z", obj.trainingCenterDatabase?.activities?.activity?.id) },
                      { assertEquals(LocalDateTime.of(2019,7,28,13,16,43), obj.trainingCenterDatabase?.activities?.activity?.lap?.startTime)}
            )
        }
    }
}
