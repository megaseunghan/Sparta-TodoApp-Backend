package com.todo.todoapplication.domain.comment.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class CommentCreateRequest(
    @field:NotBlank(message = "작성자 이메일은 필수 입력 사항입니다.")
    @field:Email(message = "올바른 이메일 형식이 아닙니다.")
    val email: String,

    @field:Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}${'$'}",
        message = "비밀번호는 영문과 특수문자 숫자를 포함하며 8자 이상이어야 합니다."
    )
    val password: String,

    @field:NotBlank(message = "댓글 내용은 필수 입력 사항입니다.")
    val content: String
)
