package strava.tpx.service

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import strava.getResource
import strava.gpx.service.GPXReader
import strava.util.modification.formatXMLToJson

internal class TXCReaderTest {

    @Nested
    inner class `When the TCX reader receives XML as a String` {

        private val objectMapper = ObjectMapper()
                .registerModule(JavaTimeModule())
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

        private val tcxReader = TCXReader(objectMapper)

        private val RAW_TCX_DATA = getResource("classpath:First_little_ride_with_Wahoo_.tcx")

        @Test
        fun `the XML is successfully parsed and converted to a GPXObject`() {
            println(formatXMLToJson(RAW_TCX_DATA))

            val obj = tcxReader.createGpxDataObjectFromJSON(RAW_TCX_DATA)
        }
    }
}
