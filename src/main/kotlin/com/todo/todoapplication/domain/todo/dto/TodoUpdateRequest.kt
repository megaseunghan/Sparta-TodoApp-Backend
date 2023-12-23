package com.todo.todoapplication.domain.todo.dto

import com.todo.todoapplication.domain.todo.model.Todo
import jakarta.validation.constraints.NotBlank

data class TodoUpdateRequest(
    @field:NotBlank
    val title: String,
    val description: String,
    val author: String
) {
    fun toEntity(): Todo {
        return Todo(
            title = title,
            description = description,
            author = author
        )
    }
}