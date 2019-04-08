package strava

import junit.framework.Assert.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension
import strava.config.StravaApplicationConfiguration
import strava.auth.buildTokenRefreshEndpoint

@ExtendWith(SpringExtension::class)
@SpringBootTest(
        classes = [StravaApplicationConfiguration::class],
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@TestPropertySource(locations = ["classpath:application.yml"])
@EnableAutoConfiguration
class StravaApplicationTest {

    @Autowired
    lateinit var applicationConfiguration: StravaApplicationConfiguration

    @Test
    fun `The application boots successfully`() {
        assertTrue(true)
    }

    @Test
    fun `The application configuration properties are injected correctly`() {
        assertNotNull(applicationConfiguration.url)
        assertNotNull(applicationConfiguration.accessToken)
        assertNotNull(applicationConfiguration.clientId)
        assertNotNull(applicationConfiguration.clientSecret)

        assertEquals("https://www.strava.com/oauth/mobile/authorize", applicationConfiguration.url)
        assertEquals("test", applicationConfiguration.accessToken)
        assertEquals("12345", applicationConfiguration.clientId)
        assertEquals("test", applicationConfiguration.clientSecret)
    }

    @Test
    fun `Strava configuration loaded`() {
        println(buildTokenRefreshEndpoint(applicationConfiguration))
    }

}
