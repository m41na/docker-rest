package works.hop.restapi.service

import com.googlecode.jmapper.JMapper
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import works.hop.restapi.entity.HoursEntity
import works.hop.restapi.entity.UserEntity
import works.hop.restapi.model.AppResult
import works.hop.restapi.model.Hours
import works.hop.restapi.model.User
import works.hop.restapi.repository.HoursRepository
import works.hop.restapi.repository.UsersRepository

interface ApiService {
    fun retrieveAllActiveUsers(): AppResult<List<User>>
    fun retrieveHoursWorkedByUser(userId: Long): AppResult<List<Hours>>
    fun saveHoursWorked(hours: Hours): AppResult<Int>
}

@Service
class UserApiService(
    val usersRepository: UsersRepository,
    val hoursRepository: HoursRepository,
    @Qualifier("UserEntityToUser") val userMapper: JMapper<User, UserEntity>,
    @Qualifier("HoursEntityToHours") val hoursMapper: JMapper<Hours, HoursEntity>
) : ApiService {

    override fun retrieveAllActiveUsers(): AppResult<List<User>> {
        return try {
            val users: List<User> = usersRepository.allActiveUsers().map { userMapper.getDestination(it) }
            return AppResult(users, null)
        } catch (th: Exception) {
            AppResult(null, th.message)
        }
    }

    override fun retrieveHoursWorkedByUser(userId: Long): AppResult<List<Hours>> {
        return try {
            val hours: List<Hours> = hoursRepository.hoursWorkedByUser(userId).map { hoursMapper.getDestination(it) }
            return AppResult(hours, null)
        } catch (th: Exception) {
            AppResult(null, th.message)
        }
    }

    override fun saveHoursWorked(hours: Hours): AppResult<Int> {
        return try {
            val updated: Int = hoursRepository.updateHours(hours.hours, hours.date, hours.id)
            return AppResult(updated, null)
        } catch (th: Exception) {
            AppResult(null, th.message)
        }
    }
}