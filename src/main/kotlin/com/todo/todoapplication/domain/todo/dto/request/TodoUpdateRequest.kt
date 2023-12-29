package com.todo.todoapplication.domain.todo.dto.request

import com.todo.todoapplication.global.exception.AUTHOR_IS_NOT_EMPTY
import com.todo.todoapplication.global.exception.TITLE_IS_NOT_EMPTY
import com.todo.todoapplication.global.exception.todo.InvalidTodoRequestException

data class TodoUpdateRequest(
    val title: String,
    val description: String,
    val author: String
) {

    init {
        require(title.isNotBlank()) { throw InvalidTodoRequestException(TITLE_IS_NOT_EMPTY) }
        require(author.isNotBlank()) { throw InvalidTodoRequestException(AUTHOR_IS_NOT_EMPTY) }
    }

}