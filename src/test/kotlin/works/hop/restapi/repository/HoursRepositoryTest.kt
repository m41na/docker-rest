package works.hop.restapi.repository

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import works.hop.restapi.config.TestDatabaseConfig
import works.hop.restapi.entity.HoursEntity
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@DataJdbcTest
@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [TestDatabaseConfig::class])
@EnableAutoConfiguration
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test", "stage")
internal class HoursRepositoryTest(@Autowired val hoursRepository: HoursRepository) {

    @Test
    fun hoursWorkedByUser() {
        val hours: List<HoursEntity> = hoursRepository.hoursWorkedByUser(1L)
        assertEquals(6, hours.size)
    }

    @Test
    fun updateHoursWorked() {
        val userId = 1L
        val hours = 20.12F
        val date = LocalDate.parse("2021-01-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        val update = hoursRepository.updateHours(hours, date, userId)
        assertEquals(1, update)
    }
}