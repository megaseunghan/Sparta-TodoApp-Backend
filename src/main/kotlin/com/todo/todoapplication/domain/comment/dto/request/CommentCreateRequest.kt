package com.todo.todoapplication.domain.comment.dto.request

import com.todo.todoapplication.domain.comment.model.Account
import com.todo.todoapplication.domain.comment.model.Comment
import com.todo.todoapplication.domain.todo.model.Todo
import com.todo.todoapplication.global.exception.message.CONTENT_IS_NOT_EMPTY
import com.todo.todoapplication.global.exception.InvalidRequestArgumentException
import com.todo.todoapplication.global.exception.message.NAME_IS_NOT_EMPTY
import com.todo.todoapplication.global.exception.message.PASSWORD_IS_NOT_VALID

data class CommentCreateRequest(
    val name: String,
    val password: String,
    val content: String
) {
    init {
        require(name.isNotEmpty()) { throw InvalidRequestArgumentException(NAME_IS_NOT_EMPTY) }
        require(validatePassword(password)) { throw InvalidRequestArgumentException(PASSWORD_IS_NOT_VALID) }
        require(content.isNotEmpty()) { throw InvalidRequestArgumentException(CONTENT_IS_NOT_EMPTY) }
    }

    fun toEntity(todo: Todo): Comment {
        return Comment(
            content = content,
            account = Account(
                name = name,
                password = password
            ),
            todo = todo
        )
    }

    private fun validatePassword(password: String): Boolean {
        return Regex(PASSWORD_REGEX).matches(password)
    }

    companion object {
        const val PASSWORD_REGEX = """^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}${'$'}"""
    }

}
