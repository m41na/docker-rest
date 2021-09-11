package works.hop.restapi.repository

import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import works.hop.restapi.entity.HoursEntity
import java.time.LocalDate

@Repository
interface HoursRepository : CrudRepository<HoursEntity, Long> {

    @Query("select * from worked_hours h where h.user_id = :userId")
    fun hoursWorkedByUser(@Param("userId") userId: Long): List<HoursEntity>

    @Modifying
    @Query(
        "insert into worked_hours (user_id, date, hours) values (:user_id, :date, :hours) on conflict (user_id, date) do " +
                "update set hours = :hours"
    )
    fun updateHours(@Param("hours") hours: Float, @Param("date") date: LocalDate, @Param("user_id") userId: Long): Int
}