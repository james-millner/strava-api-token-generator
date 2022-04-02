package strava

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension
import strava.auth.buildTokenRefreshEndpoint
import strava.config.StravaConfiguration

@ExtendWith(SpringExtension::class)
@SpringBootTest(
        classes = [StravaConfiguration::class],
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@TestPropertySource(locations = ["classpath:application.yml"])
@EnableAutoConfiguration
class StravaApplicationTest {

    @Autowired
    lateinit var stravaConfiguration: StravaConfiguration

    @Test
    fun `The application boots successfully`() {
        assertTrue(true)
    }

    @Test
    fun `The application configuration properties are injected correctly`() {
        assertNotNull(stravaConfiguration.url)
        assertNotNull(stravaConfiguration.accessToken)
        assertNotNull(stravaConfiguration.clientId)
        assertNotNull(stravaConfiguration.clientSecret)

        assertEquals("https://www.strava.com/oauth/mobile/authorize", stravaConfiguration.url)
        assertEquals("test", stravaConfiguration.accessToken)
        assertEquals("12345", stravaConfiguration.clientId)
        assertEquals("test", stravaConfiguration.clientSecret)
    }

    @Test
    fun `Strava configuration loaded`() {
        println(buildTokenRefreshEndpoint(stravaConfiguration))
    }
}
