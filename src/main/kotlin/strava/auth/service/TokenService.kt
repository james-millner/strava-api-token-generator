package strava.auth.service

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Service
import strava.auth.models.StravaToken

interface RequestTokenRepository : MongoRepository<StravaToken, String> {
    fun existsByRefreshToken(refreshToken: String): Boolean

    fun deleteByRefreshToken(refreshToken: String)
}

@Service
class TokenService(val tokenRepository: RequestTokenRepository) {

    fun deleteByRefreshToken(refreshToken: String) = tokenRepository.deleteByRefreshToken(refreshToken)

    fun save(requestStravaToken: StravaToken) =
        tokenRepository.save(requestStravaToken)

    fun existsByRefreshToken(refreshToken: String) =
        tokenRepository.existsByRefreshToken(refreshToken)
}
