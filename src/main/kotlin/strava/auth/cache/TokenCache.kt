package strava.auth.cache

import mu.KLogging
import org.springframework.cache.annotation.CacheConfig
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import strava.auth.models.StravaToken

interface TokenCacheRepository {
    fun cacheStravaToken(token: StravaToken)

    fun getStravaToken(token: StravaToken) : StravaToken?
}

@Component
class TokenCache : TokenCacheRepository {

    companion object: KLogging()

    @Scheduled(fixedDelay = 120000L)
    fun displayCache() {
        tokens.forEach{ logger.debug { "$it" }}
    }

    val tokens = mutableSetOf<StravaToken>()

    override fun cacheStravaToken(token: StravaToken) {
        logger.info { "Storing token in cache: ${token.refreshToken}" }
                .run { tokens.add(token) }
    }

    override fun getStravaToken(token: StravaToken): StravaToken? =
            tokens.find { it.refreshToken == token.refreshToken }

}
