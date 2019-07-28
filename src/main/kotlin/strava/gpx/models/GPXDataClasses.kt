package strava.gpx.models

data class GpxObject(val gpx: Gpx?)

data class Extensions(val trackpointextension: Trackpointextension?)

data class Gpx(val creator: String?, val xmlns: String?, val metadata: Metadata?, val gpxtpx: String?, val schemalocation: String?, val xsi: String?, val gpxx: String?, val trk: Trk?, val version: Number?)

data class Metadata(val time: String?)

data class Trackpointextension(val hr: Int?, val cad: Int?, val atemp: Int?)

data class Trk(val trkseg: Trkseg?, val name: String?, val type: Number?)

data class Trkpt(val extensions: Extensions?, val lon: Number?, val time: String?, val lat: Number?, val ele: Number?)

data class Trkseg(val trkpt: List<Trkpt>?)
