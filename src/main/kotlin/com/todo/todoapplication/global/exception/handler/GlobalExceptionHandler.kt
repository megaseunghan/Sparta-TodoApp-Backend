package com.todo.todoapplication.global.exception.handler

import com.todo.todoapplication.global.exception.dto.ErrorResponse
import com.todo.todoapplication.global.exception.InvalidRequestArgumentException
import com.todo.todoapplication.global.exception.NoSuchEntityException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler
    fun handleNoSuchEntityException(noSuchEntityException: NoSuchEntityException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.badRequest()
            .body(ErrorResponse.of(noSuchEntityException.message))
    }

    @ExceptionHandler
    fun handleInvalidRequestArgumentException(invalidRequestArgumentException: InvalidRequestArgumentException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.badRequest()
            .body(ErrorResponse.of(invalidRequestArgumentException.message))
    }
}