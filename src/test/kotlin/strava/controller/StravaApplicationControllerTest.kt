package strava.controller

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.mock.web.MockMultipartFile
import strava.getResource
import strava.gpx.createGpxDataObjectFromJSON
import strava.gpx.readFileToJson

internal class StravaApplicationControllerTest {

    @Nested
    inner class `When a file is received to the StravaApplicationController`() {

        private val controller = StravaApplicationController()

        private val expectedRideJSONAsString = getResource("classpath:successful-responses/afternoon-huddersfield-ride.json")
        private val expectedRideAsXML = getResource("classpath:afternoon-huddersfield-ride.gpx")
        private val afternoonRideJSONString = readFileToJson(expectedRideAsXML)

        private val expectedObject = createGpxDataObjectFromJSON(expectedRideAsXML)
        private val actualObject = createGpxDataObjectFromJSON(afternoonRideJSONString)

        private val userFile = MockMultipartFile("file", "orig", "text/plain;charset=UTF-8", expectedRideAsXML.toByteArray())

        @BeforeEach
        fun init() {
            assertNotNull(expectedObject)
            assertNotNull(actualObject)
        }

        @Test
        fun `the GPX is successfully parsed and converted to JSON`() =
                assertEquals(expectedRideJSONAsString.trim(), controller.handleFileUpload(userFile, "json").trim())

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
            assertEquals("Unsupported output type. Please select from `json`, `xml`, `dataobject` (Produces kotlin data object.toString())", exception.localizedMessage)
        }
    }
}
