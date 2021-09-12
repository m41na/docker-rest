package works.hop.rest.jwt.service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.security.Key
import java.util.*


data class UserInfo(val username: String, val password: String)

@Service
class UserJwtService : UserDetailsService {

    val fakeDB: Map<String, String> = mapOf(
        "user" to "user_pass",
        "admin" to "admin_pass",
        "guest" to "guest_pass"
    ) //fake database

    val key: Key = Keys.secretKeyFor(SignatureAlgorithm.HS256)
    val issuer = "user-jwt-service";

    override fun loadUserByUsername(username: String?): UserDetails {
        if (fakeDB.containsKey(username)) {
            return User(username, fakeDB[username], emptyList())
        }
        throw RuntimeException("user does not exist")
    }

    fun generateToken(userDetails: UserDetails): String {
        return createToken(emptyMap(), userDetails.username)
    }

    fun extractUsername(token: String): String {
        return extractClaims(token).subject
    }

    fun createToken(claims: Map<String, Any>, subject: String): String {
        return Jwts.builder()
            .setIssuer(issuer)
            .setSubject(subject)
            .addClaims(claims)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + (1000 * 60 * 60 * 2))) //2 hours
            .signWith(key)
            .compact()
    }

    fun validateToken(token: String): Boolean {
        val username = extractUsername(token)
        return (fakeDB.containsKey(username) && !isTokenExpired(token))
    }

    fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token).before(Date())
    }

    fun extractExpiration(token: String): Date {
        return extractClaims(token).expiration
    }

    fun extractClaims(token: String) : Claims {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).body //don't use parseClaimsJwt here :-)
    }
}