package works.hop.restapi.controller

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.junit.jupiter.SpringExtension
import works.hop.restapi.annotation.EnableTestConfigurations
import works.hop.restapi.model.Hours
import works.hop.restapi.validator.RequestValidator
import java.time.LocalDate
import java.util.stream.Collectors
import javax.validation.ConstraintViolation
import javax.validation.Validator
import kotlin.test.assertEquals

@ExtendWith(SpringExtension::class)
@EnableTestConfigurations
internal class HoursValidatorTest(
    @Autowired val requestValidator: Validator,
    @Autowired val hoursValidator: RequestValidator
) {

    @Test
    fun validateHoursUsingGenericFramework() {
        val hoursWorked = Hours(10L, LocalDate.now(), 101.3F)
        val violations: Set<ConstraintViolation<Hours>> = requestValidator.validate(hoursWorked)
        if (violations.isNotEmpty()) {
            val message =
                violations.stream().map { "${it.propertyPath} : ${it.message}" }.collect(Collectors.joining(","))
            println(message)
        }
    }

    @Test
    fun validateHours() {
        val hoursWorked = Hours(10L, LocalDate.now(), 101.3F)
        val violations: Set<String> = hoursValidator.validate(hoursWorked)
        assertEquals(1, violations.size)
    }
}