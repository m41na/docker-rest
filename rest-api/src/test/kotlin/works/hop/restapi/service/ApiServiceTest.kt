package works.hop.restapi.service

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import works.hop.restapi.model.AppResult
import works.hop.restapi.model.Hours
import works.hop.restapi.model.User
import java.time.LocalDate

@SpringBootTest
@TestPropertySource(locations = ["/application-test.properties"])
internal class ApiServiceTest(@Autowired @Qualifier("testApiService") val service: IApiService, @Autowired val localDate: LocalDate) {

    @Test
    fun retrieveAllActiveUsers() {
        val users: AppResult<List<User>> = service.retrieveAllActiveUsers()
        assertNotNull(users.data)
        users.data?.let { assertEquals(10, it.size) }
    }

    @Test
    fun retrieveHoursWorkedByUser() {
        val hours: AppResult<List<Hours>> = service.retrieveHoursWorkedByUser(10L)
        assertNotNull(hours.data)
        hours.data?.let { assertEquals(6, it.size) }
    }

    @Test
    fun saveHoursWorked() {
        val hours = Hours(10L, localDate, 10.0F)
        val status: AppResult<Int>  = service.saveHoursWorked(hours)
        assertEquals(0, status.data)
    }
}