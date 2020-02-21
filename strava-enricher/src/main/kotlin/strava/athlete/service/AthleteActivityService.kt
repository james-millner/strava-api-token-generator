package strava.athlete.service

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Service
import strava.athlete.model.AthleteActivity

interface AthleteActivityRepository : MongoRepository<AthleteActivity, Number>

@Service
class AthleteActivityService(val activityRepo: AthleteActivityRepository) {
    fun save(activity: AthleteActivity) =
            activityRepo.save(activity)

    fun existsById(id: Number) =
            activityRepo.existsById(id)
}
