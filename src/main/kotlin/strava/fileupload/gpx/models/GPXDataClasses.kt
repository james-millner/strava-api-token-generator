package strava.fileupload.gpx.models

import com.fasterxml.jackson.annotation.JsonProperty

data class GPXObject(
    @JsonProperty("gpx")
    val gpx: Gpx?
)

data class Gpx(
    @JsonProperty("creator")
    val creator: String?,
    @JsonProperty("xmlns")
    val xmlns: String?,
    @JsonProperty("metadata")
    val metadata: Metadata?,
    @JsonProperty("gpxtpx")
    val gpxtpx: String?,
    @JsonProperty("schemaLocation")
    val schemaLocation: String?,
    @JsonProperty("xsi")
    val xsi: String?,
    @JsonProperty("gpxx")
    val gpxx: String?,
    @JsonProperty("trk")
    val trk: Trk?,
    @JsonProperty("version")
    val version: Double?
)

data class Metadata(
    @JsonProperty("time")
    val time: String?
)

data class Trk(
    @JsonProperty("trkseg")
    val trkseg: Trkseg?,
    @JsonProperty("name")
    val name: String?,
    @JsonProperty("type")
    val type: Int?
)

data class Trkseg(
    @JsonProperty("trkpt")
    val trkpt: List<Trkpt?>?
)

data class Trkpt(
    @JsonProperty("extensions")
    val extensions: Extensions?,
    @JsonProperty("lon")
    val lon: Double?,
    @JsonProperty("time")
    val time: String?,
    @JsonProperty("lat")
    val lat: Double?,
    @JsonProperty("ele")
    val ele: Double?
)

data class Extensions(
    @JsonProperty("trackpointextension")
    val trackpointextension: Trackpointextension?,
    @JsonProperty("power")
    val power: Int
)

data class Trackpointextension(
    @JsonProperty("hr")
    val hr: Int?
)
