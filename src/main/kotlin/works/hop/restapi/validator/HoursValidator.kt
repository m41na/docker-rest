package works.hop.restapi.validator

import org.springframework.stereotype.Component
import works.hop.restapi.model.Hours

@Component
class HoursValidator : RequestValidator {

    override fun validate(target: Any): Set<String> {
        val violations = HashSet<String>()
        val hours = target as Hours
        if (hours.hours < 0) {
            violations.add("Hours should not be less than 0")
        }
        if (hours.hours > 24) {
            violations.add("Hours should not be more than 24")
        }
        return violations
    }
}