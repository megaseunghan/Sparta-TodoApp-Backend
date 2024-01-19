package com.todo.todoapplication.domain.todo.dto.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.todo.todoapplication.domain.comment.dto.response.CommentResponse
import java.time.LocalDateTime

@JsonInclude(JsonInclude.Include.NON_NULL)
data class TodoResponse(
    val id: Long,
    val title: String,
    val description: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val name: String,
    val completed: Boolean,
    var comments: List<CommentResponse>? = null
)
