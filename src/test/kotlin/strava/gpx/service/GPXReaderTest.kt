package strava.gpx.service

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import strava.getResource
import strava.util.modification.readFileToJson

internal class GPXReaderTest {

    @Nested
    inner class `When the GPX reader receives XML as a String` {

        private val objectMapper = ObjectMapper()
                .registerModule(JavaTimeModule())
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

        private val gpxReader = GPXReader(objectMapper)

        private val expectedRideJSONAsString = getResource(
                "classpath:successful-responses/afternoon-huddersfield-ride.json")
        private val afternoonRideXMLString = gpxReader.pruneGPXJson(
                readFileToJson(getResource("classpath:afternoon-huddersfield-ride.gpx")))

        private val expectedObject = gpxReader.createGpxDataObjectFromJSON(expectedRideJSONAsString)
        private val actualObject = gpxReader.createGpxDataObjectFromJSON(afternoonRideXMLString)

        @BeforeEach
        fun init() {
            assertNotNull(expectedObject)
            assertNotNull(actualObject)

            println(afternoonRideXMLString)
        }

        @Test
        fun `the XML is successfully parsed and converted to a GPXObject`() =
                assertEquals(expectedObject, actualObject)

        @Test
        fun `the XML is successfully parsed to JSON`() =
                assertEquals(afternoonRideXMLString.trim(), expectedRideJSONAsString.trim())

    }
}
