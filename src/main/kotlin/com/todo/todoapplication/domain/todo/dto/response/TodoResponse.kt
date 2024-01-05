package com.todo.todoapplication.domain.todo.dto.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.todo.todoapplication.domain.comment.dto.response.CommentResponse
import com.todo.todoapplication.domain.todo.model.Todo
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
) {
    companion object {
        fun from(todo: Todo): TodoResponse {
            return TodoResponse(
                todo.id!!,
                todo.title,
                todo.description,
                todo.createdAt,
                todo.updatedAt,
                todo.name,
                todo.completed
            )
        }

        fun from(todo: Todo, comments: List<CommentResponse>): TodoResponse {
            return TodoResponse(
                todo.id!!,
                todo.title,
                todo.description,
                todo.createdAt,
                todo.updatedAt,
                todo.name,
                todo.completed,
                comments = comments
            )
        }
    }

}
