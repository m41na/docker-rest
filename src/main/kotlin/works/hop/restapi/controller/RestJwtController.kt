package works.hop.restapi.controller

import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import works.hop.restapi.model.AuthRequest
import works.hop.restapi.model.AuthResponse
import works.hop.restapi.service.UserJwtService


@RestController
@RequestMapping("/v1/jwt")
@Validated
class RestJwtController(
    @Autowired val authenticationManager: AuthenticationManager,
    @Autowired val userJwtService: UserJwtService
) {

    @GetMapping("/health")
    @ApiOperation(
        value = "Return the status if the application is running",
        notes = "Any other response besides a 200 OK should be considered as a failure response",
        response = String::class
    )
    fun healthStatus(): ResponseEntity<String> {
        return ResponseEntity.status(200).body("Looking good")
    }

    @PostMapping("/authenticate", consumes = [MediaType.APPLICATION_JSON_VALUE])
    @ApiOperation(
        value = "Validate credentials and generate a corresponding auth token",
        notes = "This does not require and authorization token in the Headers",
        response = String::class
    )
    fun authenticate(@RequestBody authRequest: AuthRequest): ResponseEntity<AuthResponse> {
        return try {
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    authRequest.username,
                    authRequest.password
                )
            )
            val userDetails = userJwtService.loadUserByUsername(authRequest.username)
            val token = userJwtService.generateToken(userDetails)
            ResponseEntity.status(200).body(AuthResponse(token))
        } catch (ex: Exception) {
            ResponseEntity.status(403).body(AuthResponse(ex.message))
        }
    }

    @ApiOperation(
        value = "Accept a string token and validate based on whether the subject exists and whether the token is expired",
        notes = "This does not require and authorization token in the Headers",
        response = Boolean::class
    )
    @PostMapping("/validate")
    fun validate(@RequestBody token: String): ResponseEntity<Map<String, Any>> {
        if (userJwtService.validateToken(token)) {
            return ResponseEntity.status(200).body(mapOf<String, Any>("valid" to true))
        }
        return ResponseEntity.status(403).body(mapOf<String, Any>("valid" to false))
    }
}