package works.hop.restapi.filter

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import works.hop.restapi.service.UserJwtService
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtRequestFilter(@Autowired val userJwtService: UserJwtService) : OncePerRequestFilter() {

    val bearer = "Bearer " //the empty space included in bearer value is important
    val authHeaderName = "Authorization"

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader(authHeaderName)
        if (authHeader?.startsWith(bearer) == true) {
            val token = authHeader.substring(bearer.length)
            if (userJwtService.validateToken(token)) {
                val username = userJwtService.extractUsername(token)

                if (SecurityContextHolder.getContext().authentication == null) {
                    val userDetails = userJwtService.loadUserByUsername(username)
                    val tokenAuth = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                    tokenAuth.details = WebAuthenticationDetailsSource().buildDetails(request)
                    SecurityContextHolder.getContext().authentication = tokenAuth
                }
            }
        }
        //continue processing request
        filterChain.doFilter(request, response)
    }
}