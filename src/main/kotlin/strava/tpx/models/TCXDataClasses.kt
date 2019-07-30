package strava.tpx.models

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime


data class TCXObject(
        @JsonProperty("TrainingCenterDatabase")
        val trainingCenterDatabase: TrainingCenterDatabase?
)

data class TrainingCenterDatabase(
        @JsonProperty("Activities")
        val activities: Activities?,
        @JsonProperty("xmlns")
        val xmlns: String?,
        @JsonProperty("xmlns:ns2")
        val xmlnsNs2: String?,
        @JsonProperty("xsi:schemaLocation")
        val xsiSchemaLocation: String?,
        @JsonProperty("xmlns:ns5")
        val xmlnsNs5: String?,
        @JsonProperty("xmlns:ns3")
        val xmlnsNs3: String?,
        @JsonProperty("xmlns:xsi")
        val xmlnsXsi: String?
)

data class Activities(
        @JsonProperty("Activity")
        val activity: Activity?
)

data class Activity(
        @JsonProperty("Sport")
        val sport: String?,
        @JsonProperty("Lap")
        val lap: Lap?,
        @JsonProperty("Id")
        val id: String?
)

data class Lap(
        @JsonProperty("MaximumSpeed")
        val maximumSpeed: Int?,
        @JsonProperty("MaximumHeartRateBpm")
        val maximumHeartRateBpm: MaximumHeartRateBpm?,
        @JsonProperty("Intensity")
        val intensity: String?,
        @JsonProperty("StartTime")
        val startTime: LocalDateTime?,
        @JsonProperty("Cadence")
        val cadence: Int?,
        @JsonProperty("DistanceMeters")
        val distanceMeters: Double?,
        @JsonProperty("TriggerMethod")
        val triggerMethod: String?,
        @JsonProperty("TotalTimeSeconds")
        val totalTimeSeconds: Int?,
        @JsonProperty("Calories")
        val calories: Int?,
        @JsonProperty("AverageHeartRateBpm")
        val averageHeartRateBpm: AverageHeartRateBpm?,
        @JsonProperty("Track")
        val track: Track?
)

data class MaximumHeartRateBpm(
        @JsonProperty("Value")
        val value: Int?
)

data class AverageHeartRateBpm(
        @JsonProperty("Value")
        val value: Int?
)

data class Track(
        @JsonProperty("Trackpoint")
        val trackpoint: List<Trackpoint?>?
)

data class Trackpoint(
        @JsonProperty("HeartRateBpm")
        val heartRateBpm: HeartRateBpm?,
        @JsonProperty("Position")
        val position: Position?,
        @JsonProperty("Cadence")
        val cadence: Int?,
        @JsonProperty("Time")
        val time: LocalDateTime?,
        @JsonProperty("AltitudeMeters")
        val altitudeMeters: Int?,
        @JsonProperty("DistanceMeters")
        val distanceMeters: Int?,
        @JsonProperty("Extensions")
        val extensions: Extensions?
)

data class Position(
        @JsonProperty("LatitudeDegrees")
        val latitudeDegrees: Double?,
        @JsonProperty("LongitudeDegrees")
        val longitudeDegrees: Double?
)

data class HeartRateBpm(
        @JsonProperty("Value")
        val value: Int?
)

data class Extensions(
        @JsonProperty("TPX")
        val tPX: TPX?
)

data class TPX(
        @JsonProperty("Speed")
        val speed: Double?,
        @JsonProperty("xmlns")
        val xmlns: String?
)
