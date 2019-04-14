package strava.activities.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class ActivityResponse(
        val resourceState: Int?,
        val athlete: Athlete?,
        val name: String?,
        val distance: Double?,
        val movingTime: Int?,
        val elapsedTime: Int?,
        val totalElevationGain: Double?,
        val type: String?,
        val workoutType: Int?,
        @Id
        val id: Long?,
        val externalId: String?,
        val uploadId: Long?,
        val startDate: String?,
        val startDateLocal: String?,
        val timezone: String?,
        val utcOffset: Int?,
        val startLatlng: List<Double?>?,
        val endLatlng: List<Double?>?,
        val locationCity: Any?,
        val locationState: Any?,
        val locationCountry: String?,
        val startLatitude: Double?,
        val startLongitude: Double?,
        val achievementCount: Int?,
        val kudosCount: Int?,
        val commentCount: Int?,
        val athleteCount: Int?,
        val photoCount: Int?,
        val map: Map?,
        val trainer: Boolean?,
        val commute: Boolean?,
        val manual: Boolean?,
        val `private`: Boolean?,
        val visibility: String?,
        val flagged: Boolean?,
        val gearId: String?,
        val fromAcceptedTag: Boolean?,
        val averageSpeed: Double?,
        val maxSpeed: Double?,
        val averageWatts: Double?,
        val kilojoules: Double?,
        val deviceWatts: Boolean?,
        val hasHeartrate: Boolean?,
        val averageHeartrate: Double?,
        val maxHeartrate: Int?,
        val heartrateOptOut: Boolean?,
        val displayHideHeartrateOption: Boolean?,
        val elevHigh: Double?,
        val elevLow: Double?,
        val prCount: Int?,
        val totalPhotoCount: Int?,
        val hasKudoed: Boolean?,
        val sufferScore: Int?,
        val description: String?,
        val calories: Double?,
        val segmentEfforts: List<SegmentEffort?>?,
        val splitsMetric: List<SplitsMetric?>?,
        val splitsStandard: List<SplitsStandard?>?,
        val laps: List<Lap?>?,
        val gear: Gear?,
        val photos: Photos?,
        val deviceName: String?,
        val embedToken: String?,
        val availableZones: List<String?>?
)

data class Lap(
        val id: Long?,
        val resourceState: Int?,
        val name: String?,
        val activity: Activity?,
        val athlete: Athlete?,
        val elapsedTime: Int?,
        val movingTime: Int?,
        val startDate: String?,
        val startDateLocal: String?,
        val distance: Double?,
        val startIndex: Int?,
        val endIndex: Int?,
        val totalElevationGain: Double?,
        val averageSpeed: Double?,
        val maxSpeed: Double?,
        val deviceWatts: Boolean?,
        val averageWatts: Double?,
        val averageHeartrate: Double?,
        val maxHeartrate: Int?,
        val lapIndex: Int?,
        val split: Int?
)

data class Activity(
        val id: Long?,
        val resourceState: Int?
)

data class Athlete(
        val id: Int?,
        val resourceState: Int?
)

data class Photos(
        val primary: Primary?,
        val usePrimaryPhoto: Boolean?,
        val count: Int?
)

data class Primary(
        val id: Any?,
        val uniqueId: String?,
        val urls: Urls?,
        val source: Int?
)

data class Urls(
        val x100: String?,
        val x600: String?
)

data class Gear(
        val id: String?,
        val primary: Boolean?,
        val name: String?,
        val resourceState: Int?,
        val distance: Int?
)

data class Map(
        val id: String?,
        val polyline: String?,
        val resourceState: Int?,
        val summaryPolyline: String?
)

data class SegmentEffort(
        val id: Long?,
        val resourceState: Int?,
        val name: String?,
        val activity: Activity?,
        val athlete: Athlete?,
        val elapsedTime: Int?,
        val movingTime: Int?,
        val startDate: String?,
        val startDateLocal: String?,
        val distance: Double?,
        val startIndex: Int?,
        val endIndex: Int?,
        val deviceWatts: Boolean?,
        val averageWatts: Double?,
        val averageHeartrate: Double?,
        val maxHeartrate: Int?,
        val segment: Segment?,
        val komRank: Any?,
        val prRank: Any?,
        val achievements: List<Any?>?,
        val hidden: Boolean?
)

data class Segment(
        val id: Int?,
        val resourceState: Int?,
        val name: String?,
        val activityType: String?,
        val distance: Double?,
        val averageGrade: Double?,
        val maximumGrade: Double?,
        val elevationHigh: Double?,
        val elevationLow: Double?,
        val startLatlng: List<Double?>?,
        val endLatlng: List<Double?>?,
        val startLatitude: Double?,
        val startLongitude: Double?,
        val endLatitude: Double?,
        val endLongitude: Double?,
        val climbCategory: Int?,
        val city: String?,
        val state: String?,
        val country: String?,
        val `private`: Boolean?,
        val hazardous: Boolean?,
        val starred: Boolean?
)

data class SplitsMetric(
        val distance: Double?,
        val elapsedTime: Int?,
        val elevationDifference: Double?,
        val movingTime: Int?,
        val split: Int?,
        val averageSpeed: Double?,
        val averageHeartrate: Double?,
        val paceZone: Int?
)

data class SplitsStandard(
        val distance: Double?,
        val elapsedTime: Int?,
        val elevationDifference: Double?,
        val movingTime: Int?,
        val split: Int?,
        val averageSpeed: Double?,
        val averageHeartrate: Double?,
        val paceZone: Int?
)
