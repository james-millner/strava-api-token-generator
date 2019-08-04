package strava.geocoding

import com.google.maps.GeoApiContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GeocodeConfiguration(
        val geocodeConfigurationProperties: GeocodingConfigurationProperties
) {

    @Bean
    fun geoApiContext(): GeoApiContext = GeoApiContext.Builder()
            .apiKey(geocodeConfigurationProperties.apiKey)
            .build()
}
