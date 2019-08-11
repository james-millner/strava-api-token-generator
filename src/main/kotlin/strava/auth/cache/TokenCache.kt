package strava.auth.cache

import javax.annotation.PostConstruct
import mu.KLogging
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import strava.auth.models.StravaToken
import strava.auth.service.RequestTokenRepository

interface TokenCacheRepository {
    fun cacheStravaToken(token: StravaToken)

    fun getStravaToken(token: StravaToken): StravaToken?
}

@Component
class TokenCache : TokenCacheRepository {

    companion object : KLogging()

    @Scheduled(fixedDelay = 120000L)
    fun displayCache() {
        tokens.forEach { logger.info { "$it" } }
    }

    val tokens = mutableSetOf<StravaToken>()

    override fun cacheStravaToken(token: StravaToken) {
        logger.info { "Storing token in cache: ${token.refreshToken}" }
                .run { tokens.add(token) }
    }

    override fun getStravaToken(token: StravaToken): StravaToken? =
            tokens.find { it.refreshToken == token.refreshToken }
}

@Component
class TokenPopulator(val tokenCache: TokenCache, val tokenRepository: RequestTokenRepository) {

    companion object : KLogging()

    @PostConstruct
    fun populateCache() {
        tokenRepository
                .findAll()
                .forEach {
                    logger.info { "Populating cache with: $it" }
                    tokenCache.tokens.add(it)
                }
    }
}
