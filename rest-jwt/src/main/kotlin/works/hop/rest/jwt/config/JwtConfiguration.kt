package works.hop.rest.jwt.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import works.hop.rest.jwt.filter.JwtRequestFilter
import works.hop.rest.jwt.service.UserJwtService

@Configuration
@EnableWebSecurity
class JwtConfiguration(
    @Autowired val userJwtService: UserJwtService,
    @Autowired val jwtRequestFilter: JwtRequestFilter
) : WebSecurityConfigurerAdapter() {

    val swaggerList = arrayOf(
        "/v2/api-docs",
        "/configuration/**",
        "/swagger-resources/**",
        "/swagger-ui.html",
        "/webjars/**",
    )
    val permittedList = arrayOf(
        // Controller
        "/v1/jwt/validate",
        "/v1/jwt/authenticate",
    )

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return NoOpPasswordEncoder.getInstance()
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.userDetailsService(userJwtService)
    }

    override fun configure(web: WebSecurity?) {
        web?.ignoring()?.antMatchers(*swaggerList)
    }

    override fun configure(http: HttpSecurity?) {
        http?.csrf()?.disable()
            ?.sessionManagement()?.sessionCreationPolicy(SessionCreationPolicy.STATELESS) //don't create sessions please
            ?.and()?.authorizeRequests()?.antMatchers(*permittedList)?.permitAll()
            ?.and()?.authorizeRequests()?.antMatchers("/**")?.authenticated()
            ?.and()?.authorizeRequests()?.anyRequest()?.authenticated()

        // instead of sessions, use this filter to authenticate
        http?.addFilterBefore(
            jwtRequestFilter,
            UsernamePasswordAuthenticationFilter::class.java
        )
    }
}