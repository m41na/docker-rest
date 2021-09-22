package works.hop.restapi.controller

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity
import works.hop.restapi.model.AppResult
import works.hop.restapi.model.Hours
import works.hop.restapi.model.User
import works.hop.restapi.service.IApiService

@RestController
@RequestMapping("/v1/users")
class ApiController(val apiService: IApiService) {

    @GetMapping
    fun retrieveAllActiveUsers(): ResponseEntity<AppResult<List<User>>> {
        val result = apiService.retrieveAllActiveUsers()
        return when {
            result.success() -> ResponseEntity.ok(result)
            else -> ResponseEntity.status(500).body(result)
        }
    }

    @GetMapping("/{userId}/worked_hours")
    fun retrieveHoursWorkedByUser(@PathVariable userId: Long): ResponseEntity<AppResult<List<Hours>>> {
        val result = apiService.retrieveHoursWorkedByUser(userId)
        return when {
            result.success() -> ResponseEntity.ok(result)
            else -> ResponseEntity.status(500).body(result)
        }
    }

    @PostMapping(path = ["/{userId}/worked_hours"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun saveHoursWorked(@PathVariable userId: Long, @RequestBody hoursWorked: Hours): ResponseEntity<AppResult<Int>> {
        hoursWorked.id = userId
        val result = apiService.saveHoursWorked(hoursWorked)
        return when {
            result.success() -> ResponseEntity.ok(result)
            else -> ResponseEntity.status(500).body(result)
        }
    }
}