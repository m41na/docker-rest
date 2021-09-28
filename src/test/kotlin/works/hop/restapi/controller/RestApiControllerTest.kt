package works.hop.restapi.controller

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.junit.jupiter.SpringExtension
import works.hop.restapi.annotation.EnableTestConfigurations
import works.hop.restapi.model.Hours
import java.time.LocalDate

@ExtendWith(SpringExtension::class)
@EnableTestConfigurations
internal class RestApiControllerTest(@Autowired val restApiController: RestApiController) {

    @Test
    fun retrieveAllActiveUsers() {
        val users = restApiController.retrieveAllActiveUsers()
        assertNotNull(users)
        assertEquals(users.statusCode.value(), 200)
    }

    @Test
    fun retrieveHoursWorkedByUser() {
        val hours = restApiController.retrieveHoursWorkedByUser(1L)
        assertNotNull(hours)
        assertEquals(hours.statusCode.value(), 200)
    }

    @Test
    fun saveHoursWorked() {
        val hoursWorked = Hours(1L, LocalDate.now(), 18F)
        val updated = restApiController.saveHoursWorked(1L, hoursWorked)
        assertNotNull(updated)
        assertEquals(updated.statusCode.value(), 200)
    }
}