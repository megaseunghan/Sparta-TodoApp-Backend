package com.todo.todoapplication.domain.comment.dto.request

import jakarta.validation.constraints.NotBlank

data class UpdateCommentRequest(
    val name: String,
    val password: String,

    @field:NotBlank(message = "댓글 내용은 필수 입력 사항입니다.")
    val content: String
)
