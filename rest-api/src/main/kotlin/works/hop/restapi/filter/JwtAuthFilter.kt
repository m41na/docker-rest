package works.hop.restapi.filter

import feign.FeignException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import works.hop.restapi.clients.JwtAuthClient
import works.hop.restapi.clients.ValidateResponse
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
@ConditionalOnProperty("jwt.token.required", havingValue = "true")
class JwtAuthFilter(@Autowired val jwtAuthClient: JwtAuthClient) :
    OncePerRequestFilter() {

    private val log: Logger = LoggerFactory.getLogger(JwtAuthFilter::class.java)

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        val bearer = "Bearer " //the empty space included in bearer value is important
        val authHeaderName = "Authorization"

        val authHeader = request.getHeader(authHeaderName)
        if (authHeader?.startsWith(bearer) == true) {
            val token = authHeader.substring(bearer.length)
            try {
                val result: ValidateResponse? = jwtAuthClient.validate(token).body
                if (result != null) {
                    if (result.valid) {
                        //continue only if token is successfully validated
                        filterChain.doFilter(request, response)
                    } else {
                        log.error("Token validation failed")
                        response.status = 403
                        val out = response.writer
                        out.write("Unauthorized - Token validation failed")
                    }
                }
            } catch (e: FeignException) {
                log.error("Token validation failed", e)
                response.status = e.status()
                val out = response.writer
                out.write(e.contentUTF8())
            }
        }
    }
}