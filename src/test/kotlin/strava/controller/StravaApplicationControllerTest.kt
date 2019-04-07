package strava.controller

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.springframework.mock.web.MockMultipartFile
import strava.gpx.createGpxDataObjectFromJSON
import strava.gpx.readFileToJson
import test.kotlin.strava.getResource


internal class StravaApplicationControllerTest {

    @Nested
    inner class `When a file is received to the StravaApplicationController`() {

        @Test
        fun `the GPX is successfully parsed and converted to JSON`() {

            val expectedObject = readFileToJson(getResource("classpath:afternoon-huddersfield-ride.gpx"))
            val actualObject = getResource("classpath:afternoon-huddersfield-ride.gpx")
            val file = MockMultipartFile("file", "orig", "text/plain;charset=UTF-8", actualObject.toByteArray())

            val controller = StravaApplicationController()

            assertEquals(expectedObject.trim(), controller.handleFileUpload(file, "json").trim())
        }

        @Test
        fun `the GPX is successfully parsed and converted to a GPXObject`() {

            val actualObject = getResource("classpath:afternoon-huddersfield-ride.gpx")
            val expectedObject = createGpxDataObjectFromJSON(actualObject)

            val file = MockMultipartFile("file", "orig", "text/plain;charset=UTF-8", actualObject.toByteArray())

            val controller = StravaApplicationController()

            assertNotNull(expectedObject)
            assertEquals(expectedObject.toString(), controller.handleFileUpload(file, "dataobject"))
        }

        @Test
        fun `the GPX is successfully parsed and converted to XML`() {

            val actualObject = getResource("classpath:afternoon-huddersfield-ride.gpx")

            val file = MockMultipartFile("file", "orig", "text/plain;charset=UTF-8", actualObject.toByteArray())

            val controller = StravaApplicationController()

            assertNotNull(actualObject)
            assertEquals(actualObject, controller.handleFileUpload(file, "xml"))
        }

        @Test
        fun `the outputType requested is not valid and an exception is thrown`() {
            val actualObject = getResource("classpath:afternoon-huddersfield-ride.gpx")
            val file = MockMultipartFile("file", "orig", "text/plain;charset=UTF-8", actualObject.toByteArray())

            val exception = Assertions.assertThrows(Exception::class.java) {
                StravaApplicationController().handleFileUpload(file, "foobar")
            }

            assertNotNull(exception)
            assertEquals("Unsupported output type. Please select from `json`, `xml`, `dataobject` (Produces kotlin data object.toString())", exception.localizedMessage)
        }
    }


}
