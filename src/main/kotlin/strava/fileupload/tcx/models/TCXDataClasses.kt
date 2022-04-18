package strava.fileupload.tcx.models

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class TCXObject(
    @JsonProperty("TrainingCenterDatabase")
    val trainingCenterDatabase: TrainingCenterDatabase
) {
    fun getTrackPointsFromTCXObject() = trainingCenterDatabase
        .activities
        .activity
        .lap
        .track
        .trackpoint

    fun getPositionsFromTCXObjectFromTCXObject() =
        getTrackPointsFromTCXObject().map { it.position }
}

data class TrainingCenterDatabase(
    @JsonProperty("Activities")
    val activities: Activities,
    @JsonProperty("xmlns")
    val xmlns: String,
    @JsonProperty("xmlns:ns2")
    val xmlnsNs2: String,
    @JsonProperty("xmlns:ns3")
    val xmlnsNs3: String,
    @JsonProperty("xmlns:ns5")
    val xmlnsNs5: String,
    @JsonProperty("xmlns:xsi")
    val xmlnsXsi: String,
    @JsonProperty("xsi:schemaLocation")
    val xsiSchemaLocation: String
)

data class Activities(
    @JsonProperty("Activity")
    val activity: Activity
)

data class Activity(
    @JsonProperty("Id")
    val id: String,
    @JsonProperty("Lap")
    val lap: Lap,
    @JsonProperty("Sport")
    val sport: String
)

data class Lap(
    @JsonProperty("AverageHeartRateBpm")
    val averageHeartRateBpm: AverageHeartRateBpm,
    @JsonProperty("Cadence")
    val cadence: Int,
    @JsonProperty("Calories")
    val calories: Int,
    @JsonProperty("DistanceMeters")
    val distanceMeters: Double,
    @JsonProperty("Intensity")
    val intensity: String,
    @JsonProperty("MaximumHeartRateBpm")
    val maximumHeartRateBpm: MaximumHeartRateBpm,
    @JsonProperty("MaximumSpeed")
    val maximumSpeed: Int,
    @JsonProperty("StartTime")
    val startTime: String,
    @JsonProperty("TotalTimeSeconds")
    val totalTimeSeconds: Int,
    @JsonProperty("Track")
    val track: Track,
    @JsonProperty("TriggerMethod")
    val triggerMethod: String
)

data class AverageHeartRateBpm(
    @JsonProperty("Value")
    val value: Int
)

data class MaximumHeartRateBpm(
    @JsonProperty("Value")
    val value: Int
)

data class Track(
    @JsonProperty("Trackpoint")
    val trackpoint: List<Trackpoint>
)

data class Trackpoint(
    @JsonProperty("AltitudeMeters")
    val altitudeMeters: Double,
    @JsonProperty("Cadence")
    val cadence: Int,
    @JsonProperty("DistanceMeters")
    val distanceMeters: Int,
    @JsonProperty("Extensions")
    val extensions: Extensions,
    @JsonProperty("HeartRateBpm")
    val heartRateBpm: HeartRateBpm,
    @JsonProperty("Position")
    val position: Position,
    @JsonProperty("Time")
    val time: String
)

data class Extensions(
    @JsonProperty("TPX")
    val tPX: TPX
)

data class HeartRateBpm(
    @JsonProperty("Value")
    val value: Int
)

data class Position(
    @JsonProperty("LatitudeDegrees")
    val latitudeDegrees: Double,
    @JsonProperty("LongitudeDegrees")
    val longitudeDegrees: Double
)

data class TPX(
    @JsonProperty("Speed")
    val speed: Int,
    @JsonProperty("xmlns")
    val xmlns: String
)