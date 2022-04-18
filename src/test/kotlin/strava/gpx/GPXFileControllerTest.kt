package strava.gpx

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.mock.web.MockMultipartFile
import strava.fileupload.gpx.GPXFileController
import strava.getResource
import strava.fileupload.gpx.service.GPXReader
import strava.util.modification.readFileToJson

internal class GPXFileControllerTest {

    @Nested
    inner class `When a file is received to the StravaApplicationController`() {

        private val objectMapper = ObjectMapper()
                .registerModule(JavaTimeModule())
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

        private val gpxReader = GPXReader(objectMapper)

        private val controller = GPXFileController(gpxReader)

        private val expectedRideJSONAsString = getResource(
                "classpath:successful-responses/afternoon-huddersfield-ride.json")
        private val expectedRideAsXML = getResource("classpath:gpx/afternoon-huddersfield-ride.gpx")
        private val afternoonRideJSONString = gpxReader.pruneGPXJson(readFileToJson(expectedRideAsXML))

        private val expectedObject = gpxReader.createGpxDataObjectFromJSON(expectedRideAsXML)
        private val actualObject = gpxReader.createGpxDataObjectFromJSON(afternoonRideJSONString)

        private val userFile = MockMultipartFile("file", "orig", "text/plain;charset=UTF-8",
                                                 expectedRideAsXML.toByteArray())

        @BeforeEach
        fun init() {
            assertNotNull(expectedObject)
            assertNotNull(actualObject)
        }

        @Test
        fun `the GPX is successfully parsed and converted to JSON`() {
            assertEquals(expectedRideJSONAsString.trim(), controller.handleFileUpload(userFile, "json").trim())
        }


        @Test
        fun `the GPX is successfully parsed and converted to a GPXObject`() =
                assertEquals(expectedObject.toString(), controller.handleFileUpload(userFile, "dataobject"))

        @Test
        fun `the GPX is successfully parsed and converted to XML`() =
                assertEquals(expectedRideAsXML, controller.handleFileUpload(userFile, "xml"))

        @Test
        fun `the outputType requested is not valid and an exception is thrown`() {
            val exception = Assertions.assertThrows(Exception::class.java) {
                controller.handleFileUpload(userFile, "foobar")
            }

            assertNotNull(exception)
            assertEquals(
                    "Unsupported output type. Please select from `json`, `xml`, `dataobject` (Produces kotlin data object.toString())",
                    exception.localizedMessage)
        }
    }
}
