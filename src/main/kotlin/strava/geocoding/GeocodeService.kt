package strava.geocoding

import com.google.maps.GeoApiContext
import com.google.maps.GeocodingApi
import com.google.maps.model.GeocodingResult
import com.google.maps.model.LatLng
import mu.KLogging
import org.springframework.stereotype.Service
import strava.tcx.models.Position
import strava.tcx.models.TCXObject

@Service
class GeocodeService(
    val geocodeContext: GeoApiContext
) {

    fun getAddressInformationForTcxObject(tcxObject: TCXObject): List<GeocodingResult> =
            tcxObject.getTrackPointsFromTCXObject().map {
                getGeocodeResultForPosition(it.position).first()
            }

    private fun getGeocodeResultForPosition(position: Position): Array<out GeocodingResult> =
            GeocodingApi.reverseGeocode(
                    geocodeContext,
                    LatLng(position.latitudeDegrees, position.longitudeDegrees)).await()

    companion object : KLogging()
}
