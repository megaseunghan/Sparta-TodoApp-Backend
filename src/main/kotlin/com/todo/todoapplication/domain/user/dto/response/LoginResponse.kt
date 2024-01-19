package com.todo.todoapplication.domain.user.dto.response

data class LoginResponse(
    val grantType: String,
    val accessToken: String
)