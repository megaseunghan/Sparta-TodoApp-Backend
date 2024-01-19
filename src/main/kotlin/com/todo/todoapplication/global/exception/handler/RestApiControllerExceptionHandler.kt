package com.todo.todoapplication.global.exception.handler

import com.todo.todoapplication.global.exception.AccessDeniedApiException
import com.todo.todoapplication.global.exception.DuplicatedEmailException
import com.todo.todoapplication.global.exception.IdPasswordMismatchException
import com.todo.todoapplication.global.exception.NoSuchEntityException
import com.todo.todoapplication.global.exception.response.ErrorResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class RestApiControllerExceptionHandler {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler
    fun handleNoSuchEntityException(exception: NoSuchEntityException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse.of(HttpStatus.NOT_FOUND, exception.message!!)
        logger.error(exception.stackTraceToString())
        return ResponseEntity.status(errorResponse.status)
            .body(errorResponse)
    }

    @ExceptionHandler
    fun handleInvalidRequestArgumentException(exception: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val bindingResult = exception.bindingResult
        logger.error(exception.stackTraceToString())
        return ResponseEntity.badRequest()
            .body(
                ErrorResponse.of(
                    httpStatus = HttpStatus.BAD_REQUEST,
                    message = "잘못된 입력값입니다.",
                    bindingResult = bindingResult
                )
            )
    }

    @ExceptionHandler
    fun handleIdPasswordMismatchException(exception: IdPasswordMismatchException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse.of(HttpStatus.BAD_REQUEST, exception.message!!)
        logger.error(exception.stackTraceToString())
        return ResponseEntity.status(errorResponse.status)
            .body(errorResponse)
    }

    @ExceptionHandler
    fun handleDuplicatedEmailException(exception: DuplicatedEmailException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse.of(HttpStatus.BAD_REQUEST, exception.message!!)
        logger.error(exception.stackTraceToString())
        return ResponseEntity.badRequest()
            .body(errorResponse)
    }

    @ExceptionHandler
    fun handleAccessDeniedApiException(exception: AccessDeniedApiException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse.of(HttpStatus.FORBIDDEN, exception.message!!)
        logger.error(exception.stackTraceToString())
        return ResponseEntity.status(errorResponse.status).body(errorResponse)
    }

}