package com.todo.todoapplication.global.exception

data class ErrorResponse(
    private val message: String?
) {

    companion object {
        fun of(message: String?): ErrorResponse {
            return ErrorResponse(message)
        }
    }

}