package com.todo.todoapplication.domain.todo.dto

import com.todo.todoapplication.domain.todo.model.Todo
import java.time.LocalDateTime

data class TodoResponse(
    val title: String,
    val description: String,
    val creationTime: LocalDateTime,
    val author: String
) {
    companion object {
        fun from(todo: Todo): TodoResponse {
            return TodoResponse(
                title = todo.title,
                description = todo.description,
                creationTime = todo.creationTime,
                author = todo.author
            )
        }
    }
}
