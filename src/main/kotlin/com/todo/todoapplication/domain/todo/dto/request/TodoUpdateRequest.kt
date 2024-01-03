package com.todo.todoapplication.domain.todo.dto.request

import com.todo.todoapplication.global.exception.message.NAME_IS_NOT_EMPTY
import com.todo.todoapplication.global.exception.message.TITLE_IS_NOT_EMPTY
import com.todo.todoapplication.global.exception.InvalidRequestArgumentException

data class TodoUpdateRequest(
    val title: String,
    val description: String,
    val name: String
) {

    init {
        require(title.isNotBlank()) { throw InvalidRequestArgumentException(TITLE_IS_NOT_EMPTY) }
        require(name.isNotBlank()) { throw InvalidRequestArgumentException(NAME_IS_NOT_EMPTY) }
    }

}