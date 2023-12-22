package com.todo.todoapplication.domain.todo.dto

import com.todo.todoapplication.domain.todo.model.Todo

data class TodoResponse(
    val title: String,
    val description: String,
    val author: String
) {
    companion object {
        fun from(todo: Todo): TodoResponse {
            return TodoResponse(
                title = todo.title,
                description = todo.description,
                author = todo.author
            )
        }
    }
}
