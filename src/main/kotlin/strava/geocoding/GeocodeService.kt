package strava.geocoding

import com.google.maps.GeoApiContext
import com.google.maps.GeocodingApi
import mu.KLogging
import org.springframework.stereotype.Service
import strava.tpx.models.TCXObject

@Service
class GeocodeService(
        val geocodeContext: GeoApiContext
) {

    fun getAddressInformation(tcxObject: TCXObject): TCXObject {

        val result = tcxObject.trainingCenterDatabase
                ?.activities
                ?.activity
                ?.lap
                ?.track
                ?.trackpoint?.get(0)

        val position = result?.position ?:
        throw Exception("No position available for $result")

        val resultGeo = GeocodingApi.geocode(
                geocodeContext,
                "${position.latitudeDegrees} ${position.longitudeDegrees}"
        ).await()

        logger.info { "Received ${resultGeo.size} results" }
        val first = resultGeo.first()

        logger.info { first.formattedAddress }
        logger.info { first.geometry }

        return tcxObject
    }

    companion object: KLogging()

}
