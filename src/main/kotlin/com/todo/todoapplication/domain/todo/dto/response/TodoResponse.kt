package com.todo.todoapplication.domain.todo.dto.response

import com.todo.todoapplication.domain.comment.model.Comment
import com.todo.todoapplication.domain.todo.model.Todo
import java.time.LocalDateTime

data class TodoResponse(
    val id: Long,
    val title: String,
    val description: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val author: String,
    val completed: Boolean,
    var comments: List<Comment>? = null
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

        fun from(todo: Todo, comments: List<Comment>): TodoResponse {
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
