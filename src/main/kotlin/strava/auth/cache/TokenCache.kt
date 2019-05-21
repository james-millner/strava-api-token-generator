package strava.auth.cache

import mu.KLogging
import org.springframework.cache.annotation.CacheConfig
import org.springframework.stereotype.Component
import strava.auth.models.StravaToken

interface TokenCacheRepository {
    fun cacheStravaToken(token: StravaToken)

    fun getStravaToken(token: StravaToken) : StravaToken?
}

@CacheConfig(cacheNames=["tokens"])
@Component
class TokenCache : TokenCacheRepository {

    companion object: KLogging()

    val tokens = mutableListOf<StravaToken>()

    override fun cacheStravaToken(token: StravaToken) {
        logger.info { "Storing token in cache: ${token.refreshToken}" }
                .run { tokens.add(token) }
    }

    override fun getStravaToken(token: StravaToken): StravaToken? =
            tokens.find { it.refreshToken == token.refreshToken }

}
