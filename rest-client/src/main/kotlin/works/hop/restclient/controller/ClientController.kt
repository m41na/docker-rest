package works.hop.restclient.controller

import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange

@RestController
@RequestMapping("/v1/client")
class ClientController(val restTemplate: RestTemplate) {

    val jwtServerUrl = "https://localhost:3443/v1/jwt/health"

    @GetMapping("/health")
    fun checkJwtServerStatus(): ResponseEntity<String> {
        //Set the headers you need send
        val headers = HttpHeaders()
        headers.set(
            "Authorization",
            "Bearer eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJ1c2VyLWp3dC1zZXJ2aWNlIiwic3ViIjoidXNlciIsImlhdCI6MTYzMjM1MTg5MCwiZXhwIjoxNjMyMzU5MDkwfQ.IAKoAQFFBCkU3vmhlxUSJs3vXoqMYulK8IaFn2EYJY0"
        )

        //Create a new HttpEntity
        val entity: HttpEntity<String> = HttpEntity<String>(headers)

        //Execute the method writing your HttpEntity to the request
        return restTemplate.exchange(jwtServerUrl, HttpMethod.GET, entity, String::class)
    }
}