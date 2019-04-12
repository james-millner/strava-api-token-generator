package strava.auth

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Service

interface RequestTokenRepository : MongoRepository<RequestTokenResponse, String>

@Service
class TokenService(val tokenRepository: RequestTokenRepository) {
    fun save(requestToken: RequestTokenResponse) =
            tokenRepository.save(requestToken)

    fun existsByToken(token: String) =
            tokenRepository.existsById(token)
}
