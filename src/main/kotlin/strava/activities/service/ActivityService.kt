package strava.activities.service

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Service
import strava.activities.models.ActivityResponse

interface ActivityRepository : MongoRepository<ActivityResponse, Long>

@Service
class ActivityService(val activityRepository: ActivityRepository)  {
    fun save(activity: ActivityResponse) =
            activityRepository.save(activity)

    fun existsById(id: Long) =
            activityRepository.existsById(id)
}
