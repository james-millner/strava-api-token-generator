package strava.gpx

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import test.kotlin.strava.getResource

internal class GPXReaderTest {

    @Nested
    inner class `When the GPX reader receives XML as a String` {

        private val expectedRideJSONAsString = getResource("classpath:successful-responses/afternoon-huddersfield-ride.json")
        private val afternoonRideXMLString = readFileToJson(getResource("classpath:afternoon-huddersfield-ride.gpx"))

        private val expectedObject = createGpxDataObjectFromJSON(expectedRideJSONAsString)
        private val actualObject = createGpxDataObjectFromJSON(afternoonRideXMLString)

        @BeforeEach
        fun init() {
            assertNotNull(expectedObject)
            assertNotNull(actualObject)
        }

        @Test
        fun `the XML is successfully parsed and converted to a GPXObject`() =
            assertEquals(expectedObject, actualObject)

        @Test
        fun `the XML is successfully parsed to JSON`() =
            assertEquals(afternoonRideXMLString.trim(), expectedRideJSONAsString.trim())

    }
}
