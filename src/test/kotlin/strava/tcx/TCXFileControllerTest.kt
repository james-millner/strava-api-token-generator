package strava.tcx

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.mockk.mockk
import org.junit.jupiter.api.*
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.mock.web.MockMultipartFile
import strava.fileupload.tcx.TCXFileController
import strava.fileupload.tcx.service.TCXReader
import strava.geocoding.GeocodeConfiguration
import strava.geocoding.GeocodeService
import strava.getResource


internal class TCXFileControllerTest {

    @Nested
    inner class `When a file is received to the TCXFileController`() {

        private val objectMapper = ObjectMapper()
            .registerModule(JavaTimeModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

        private val tcxReader = TCXReader(objectMapper)

        private val mockGeoConfig = mockk<GeocodeConfiguration>()
        private val mockGeocodeService = mockk<GeocodeService>()
        private val mockMongoTemplate = mockk<MongoTemplate>()

        private val controller = TCXFileController(tcxReader, mockGeoConfig, mockGeocodeService, mockMongoTemplate)

        private val expectTCXAsXML = getResource(
            "classpath:tcx/First_little_ride_with_Wahoo.tcx")
        private val expectedRideAsJson = getResource("classpath:tcx/First_little_ride_with_Wahoo.json")

        private val userFile = MockMultipartFile("file", "orig", "text/plain;charset=UTF-8",
            expectTCXAsXML.toByteArray())

        @Test
        fun `the TCX is successfully parsed and converted to JSON`() {
            Assertions.assertEquals(
                expectedRideAsJson.trim(),
                controller.handleFileConvert(userFile, "json").trim()
            )
        }


        @Test
        fun `the TCX is successfully parsed and converted to a GPXObject`() {
            Assertions.assertEquals(
                tcxReader.createTcxDataObjectFromJSON(expectTCXAsXML).toString(),
                controller.handleFileConvert(userFile, "dataobject").trim()
            )
        }

        @Test
        fun `the TCX is successfully parsed and converted to XML`() {
            Assertions.assertEquals(
                expectTCXAsXML.trim(),
                controller.handleFileConvert(userFile, "xml").trim()
            )
        }

        @Test
        fun `the outputType requested is not valid and an exception is thrown d`() {
            val exception = Assertions.assertThrows(Exception::class.java) {
                controller.handleFileConvert(userFile, "foobar")
            }

            Assertions.assertNotNull(exception)
            Assertions.assertEquals(
                "Unsupported output type. Please select from `json`, `xml`, `dataobject` (Produces kotlin data object.toString())",
                exception.localizedMessage
            )
        }
    }
}