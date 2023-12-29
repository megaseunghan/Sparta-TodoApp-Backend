package com.todo.todoapplication.global.exception.handler

import com.todo.todoapplication.global.exception.ErrorResponse
import com.todo.todoapplication.global.exception.todo.InvalidTodoRequestException
import com.todo.todoapplication.global.exception.todo.NoSuchTodoException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler
    fun handleTodoNotFoundException(noSuchTodoException: NoSuchTodoException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.badRequest()
            .body(ErrorResponse.of(noSuchTodoException.message))
    }

    @ExceptionHandler
    fun handleInvalidTodoRequestException(invalidTodoRequestException: InvalidTodoRequestException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.badRequest()
            .body(ErrorResponse.of(invalidTodoRequestException.message))
    }
}