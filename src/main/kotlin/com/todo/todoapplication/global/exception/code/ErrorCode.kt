package com.todo.todoapplication.global.exception.code

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val status: HttpStatus,
    val message: String
) {
}







