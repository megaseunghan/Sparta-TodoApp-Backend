package com.todo.todoapplication.global.exception.response

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.http.HttpStatus
import org.springframework.validation.BindingResult

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ErrorResponse(
    val status: HttpStatus,
    val message: String,
    val fieldError: List<FieldError>?
) {

    class FieldError(
        val field: String,
        val value: String,
        val message: String
    ) {
        companion object {
            fun of(bindingResult: BindingResult): List<FieldError> {
                val fieldErrors = bindingResult.fieldErrors

                return fieldErrors.stream()
                    .map { error ->
                        FieldError(
                            field = error.field,
                            value = error.rejectedValue.toString(),
                            message = error.defaultMessage.toString()
                        )
                    }.toList()
            }

        }

    }

    companion object {
        fun of(httpStatus: HttpStatus, message: String): ErrorResponse {
            return ErrorResponse(httpStatus, message, null)
        }

        fun of(httpStatus: HttpStatus, message: String, bindingResult: BindingResult): ErrorResponse {
            return ErrorResponse(httpStatus, message, FieldError.of(bindingResult))
        }

    }

}