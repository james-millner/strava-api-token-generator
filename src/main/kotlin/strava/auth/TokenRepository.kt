package strava.auth

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Service

interface RequestTokenRepository : MongoRepository<StravaToken, String> {
    fun findByRefreshToken(refreshToken: String): StravaToken?

    fun findByAccessToken(accessToken: String): StravaToken?
}

@Service
class TokenService(val tokenRepository: RequestTokenRepository)  {
    fun save(requestStravaToken: StravaToken) =
            tokenRepository.save(requestStravaToken)

    fun existsByToken(token: String) =
            tokenRepository.existsById(token)

    fun findByRefreshToken(refreshToken: String) =
            tokenRepository.findByRefreshToken(refreshToken)

    fun findByAccessToken(accessToken: String) =
            tokenRepository.findByAccessToken(accessToken)
}
