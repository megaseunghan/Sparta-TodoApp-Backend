package com.todo.todoapplication.global.auth.jwt

import com.todo.todoapplication.global.auth.service.CustomUserDetailService
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpHeaders
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets

@Component
class JwtTokenResolver(
    private val customUserDetailService: CustomUserDetailService,
    jwtProperties: JwtProperties,
) {

    private val key = Keys.hmacShaKeyFor(jwtProperties.secret.toByteArray(StandardCharsets.UTF_8))

    companion object {
        private val BEARER_PATTERN = Regex("^Bearer (.*?)$")
    }

    fun resolveToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION) ?: return null
        return BEARER_PATTERN.find(bearerToken)?.groupValues?.get(1)
    }

    fun getUserDetails(accessToken: String): UserDetails {
        val claims = getClaims(accessToken)

        return customUserDetailService.loadUserByUsername(claims.subject)
    }

    fun validateToken(accessToken: String): Boolean {
        return kotlin.runCatching {
            getClaims(accessToken)
        }.isSuccess
    }

    fun getClaims(accessToken: String): Claims {
        return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(accessToken)
            .payload
    }

}