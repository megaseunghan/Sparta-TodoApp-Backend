package com.todo.todoapplication.domain.comment.dto.request

import com.todo.todoapplication.global.exception.message.CONTENT_IS_NOT_EMPTY
import com.todo.todoapplication.global.exception.InvalidRequestArgumentException

data class UpdateCommentRequest(
    val name: String,
    val password: String,
    val content: String
) {

    init {
        require(content.isNotEmpty()) { throw InvalidRequestArgumentException(CONTENT_IS_NOT_EMPTY) }
    }
}
