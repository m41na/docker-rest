package works.hop.restapi.controller

import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import works.hop.restapi.model.AppResult
import works.hop.restapi.model.Hours
import works.hop.restapi.model.User
import works.hop.restapi.service.IApiService
import javax.validation.constraints.Min
import javax.validation.constraints.Positive

@RestController
@RequestMapping("/v1/users")
@Validated
class ApiController(val apiService: IApiService) {

    @ApiOperation(
        value = "`GET` all active users",
        notes = "`curl http://localhost:3000/v1/users` should return a list of users in a JSON object",
    )
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "List of users retrieved successfully"),
            ApiResponse(code = 500, message = "Failed to retrieve users list")
        ]
    )
    @GetMapping
    fun retrieveAllActiveUsers(): ResponseEntity<AppResult<List<User>>> {
        val result = apiService.retrieveAllActiveUsers()
        return when {
            result.success() -> ResponseEntity.ok(result)
            else -> ResponseEntity.status(500).body(result)
        }
    }

    @ApiOperation(
        value = "`GET` all worked_hours for users",
        notes = "`curl http://localhost:3000/v1/users/1/worked_hours` should return 6 records",
    )
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "List of hours worked retrieved successfully"),
            ApiResponse(code = 400, message = "Invalid request parameters"),
            ApiResponse(code = 500, message = "Failed to retrieve hours worked")
        ]
    )
    @GetMapping("/{userId}/worked_hours")
    fun retrieveHoursWorkedByUser(@PathVariable @Positive(message = "A valid user id is required") userId: Long): ResponseEntity<AppResult<List<Hours>>> {
        val result = apiService.retrieveHoursWorkedByUser(userId)
        return when {
            result.success() -> ResponseEntity.ok(result)
            else -> ResponseEntity.status(500).body(result)
        }
    }

    @ApiOperation(
        value = "`POST` a new worked_hours record",
        notes = "if no record exists, a new one is created, but if one exists, the hours value is updated",
    )
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "Hours worked updated successfully"),
            ApiResponse(code = 400, message = "Invalid request parameters"),
            ApiResponse(code = 500, message = "Failed to update hours worked")
        ]
    )
    @PostMapping(path = ["/{userId}/worked_hours"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun saveHoursWorked(
        @PathVariable @Positive(message = "A valid user id is required") userId: Long,
        @RequestBody @Min(0) hoursWorked: Hours
    ): ResponseEntity<AppResult<Int>> {
        hoursWorked.id = userId
        val result = apiService.saveHoursWorked(hoursWorked)
        return when {
            result.success() -> ResponseEntity.ok(result)
            else -> ResponseEntity.status(500).body(result)
        }
    }
}