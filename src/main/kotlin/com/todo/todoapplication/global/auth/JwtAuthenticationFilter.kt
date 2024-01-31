package com.todo.todoapplication.global.auth

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetails
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtTokenResolver: JwtTokenResolver
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = jwtTokenResolver.resolveToken(request)

        token?.let {
            if (jwtTokenResolver.validateToken(token)) {
                val userDetails = jwtTokenResolver.getUserDetails(token)

                UsernamePasswordAuthenticationToken.authenticated(userDetails, "", userDetails.authorities)
                    .apply { details = WebAuthenticationDetails(request) }
                    .also { SecurityContextHolder.getContext().authentication = it }
            }
        } ?: RuntimeException("JWT TOKEN 없음")

        filterChain.doFilter(request, response)
    }

}