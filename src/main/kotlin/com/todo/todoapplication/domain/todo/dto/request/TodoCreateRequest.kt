package com.todo.todoapplication.domain.todo.dto.request

import com.todo.todoapplication.global.exception.AUTHOR_IS_NOT_EMPTY
import com.todo.todoapplication.global.exception.TITLE_IS_NOT_EMPTY
import com.todo.todoapplication.domain.todo.model.Todo
import com.todo.todoapplication.global.exception.todo.InvalidTodoRequestException

data class TodoCreateRequest(
    val title: String,
    val description: String,
    val authorName: String
) {
    init {
        require(title.isNotBlank()) { throw InvalidTodoRequestException(TITLE_IS_NOT_EMPTY) }
        require(authorName.isNotBlank()) { throw InvalidTodoRequestException(AUTHOR_IS_NOT_EMPTY) }
    }

    fun toEntity(): Todo {
        return Todo(
            title = title,
            description = description,
            author = authorName
        )
    }
}
