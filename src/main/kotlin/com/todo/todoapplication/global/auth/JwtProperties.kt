package com.todo.todoapplication.global.auth

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "jwt")
data class JwtProperties (
    val issuer: String,
    val secret: String,
    val accessTokenExpirationHour: Long
)