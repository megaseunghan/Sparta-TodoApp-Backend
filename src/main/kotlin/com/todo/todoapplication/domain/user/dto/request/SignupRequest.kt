package com.todo.todoapplication.domain.user.dto.request

import com.todo.todoapplication.domain.user.model.UserRole
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern

data class SignupRequest(
    @field:Email(message = "올바른 이메일 형식이 아닙니다")
    @field:NotBlank
    val email: String,

    @field:NotBlank
    @field:Pattern(
        regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[@#$%^&+=!])(?=\\S+$).{8,15}$",
        message = "비밀번호는 8~20 소/대/특수문자를 혼합하여 작성해주세요"
    )
    val password: String,

    @field:NotNull(message = "역할은 필수값입니다.")
    val role: UserRole
)