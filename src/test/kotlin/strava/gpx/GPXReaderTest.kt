package strava.gpx

import org.apache.commons.io.IOUtils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.util.ResourceUtils
import java.io.FileInputStream
import java.nio.charset.StandardCharsets

internal class GPXReaderTest {

    @Nested
    inner class `When the GPX reader receives XML as a String` {

        fun getResource(resource: String): String {
            val file = ResourceUtils.getFile(resource)
            val inputStream = FileInputStream(file)
            return IOUtils.toString(inputStream, StandardCharsets.UTF_8.name())
        }

        @Test
        fun `the XML is successfully parsed and converted to a GPXObject`() {

            val expectedObject = createGpxDataObjectFromJSON(getResource("classpath:successful-responses/afternoon-huddersfield-ride.json"))
            val actualObject = createGpxDataObjectFromJSON(readFileToJson(getResource("classpath:afternoon-huddersfield-ride.gpx")))

            assertNotNull(expectedObject)
            assertEquals(expectedObject, actualObject)
        }

        @Test
        fun `the XML is successfully parsed to JSON`() {
            val expectedObject = readFileToJson(getResource("classpath:afternoon-huddersfield-ride.gpx"))
            val actualObject = getResource("classpath:successful-responses/afternoon-huddersfield-ride.json")


            assertNotNull(actualObject)
            assertEquals(expectedObject.trim(), actualObject.trim())
        }

    }
}
