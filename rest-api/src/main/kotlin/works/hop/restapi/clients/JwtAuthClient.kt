package works.hop.restapi.clients

import feign.Headers
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.data.repository.query.Param
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping

data class ValidateResponse(val valid: Boolean)

@FeignClient(value = "jwt-auth-client", url = "\${jwt.auth.client.url}")
interface JwtAuthClient {

    @Headers("Authorization: Bearer {access_token}")
    @PostMapping("/validate")
    fun validate(@Param("access_token") accessToken: String): ResponseEntity<ValidateResponse>
}