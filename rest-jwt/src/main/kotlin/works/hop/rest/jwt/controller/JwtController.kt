package works.hop.rest.jwt.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.*
import works.hop.rest.jwt.model.AuthRequest
import works.hop.rest.jwt.model.AuthResponse
import works.hop.rest.jwt.service.UserJwtService


@RestController
@RequestMapping("v1/jwt")
class JwtController(
    @Autowired val authenticationManager: AuthenticationManager,
    @Autowired val userJwtService: UserJwtService
) {

    @GetMapping("/health")
    fun healthStatus(): ResponseEntity<String>{
        return ResponseEntity.status(200).body("Looking good")
    }

    @PostMapping("/authenticate", consumes = [MediaType.APPLICATION_JSON_VALUE])
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

    @PostMapping("/validate")
    fun validate(@RequestBody token: String) : ResponseEntity<Map<String, Any>>{
        if(userJwtService.validateToken(token)) {
            return ResponseEntity.status(200).body(mapOf<String, Any>("valid" to true))
        }
        return  ResponseEntity.status(403).body(mapOf<String, Any>("valid" to false));
    }
}