package strava

import junit.framework.Assert.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension
import strava.auth.StravaApplicationConfiguration

@ExtendWith(SpringExtension::class)
@SpringBootTest(
        classes = [StravaApplicationConfiguration::class],
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@TestPropertySource(locations=["classpath:application.yml"])
@EnableAutoConfiguration
class StravaApplicationTest {

    @Autowired lateinit var applicationConfiguration: StravaApplicationConfiguration

    @Test
    fun `The application boots successfully`() {
        assertTrue(true)
    }

    @Test
    fun `The application configuration properties are injected correctly`() {
        assertNotNull(applicationConfiguration.accessToken)
        assertNotNull(applicationConfiguration.clientId)
        assertNotNull(applicationConfiguration.clientSecret)

        assertEquals("test", applicationConfiguration.accessToken)
        assertEquals("test", applicationConfiguration.clientId)
        assertEquals("test", applicationConfiguration.clientSecret)

    }

}
