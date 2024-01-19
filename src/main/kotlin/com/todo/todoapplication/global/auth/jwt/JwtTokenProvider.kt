package com.todo.todoapplication.global.auth.jwt

import com.todo.todoapplication.domain.user.dto.response.LoginResponse
import com.todo.todoapplication.domain.user.model.User
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.time.Duration
import java.time.Instant
import java.util.*

@Component
class JwtTokenProvider(
    private val jwtProperties: JwtProperties
) {

    private val key = Keys.hmacShaKeyFor(jwtProperties.secret.toByteArray(StandardCharsets.UTF_8))

    fun generateAccessToken(user: User): LoginResponse {
        val now = Instant.now()
        val role = user.role.name

        val claims: Claims = Jwts.claims()
            .add(mapOf("role" to role))
            .build()

        val accessToken = Jwts.builder()
            .subject(user.email)
            .issuer(jwtProperties.issuer)
            .issuedAt(Date.from(now))
            .expiration(Date.from(now.plus(Duration.ofHours(jwtProperties.accessTokenExpirationHour))))
            .claims(claims)
            .signWith(key)
            .compact()

        return LoginResponse("Bearer", accessToken)
    }

}
