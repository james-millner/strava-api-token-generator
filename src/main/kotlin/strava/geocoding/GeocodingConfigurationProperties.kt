package strava.geocoding

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "strava.geocoding")
@EnableAsync
@EnableConfigurationProperties
data class GeocodingConfigurationProperties(
    var enabled: Boolean? = false,
    var apiKey: String? = null
)
