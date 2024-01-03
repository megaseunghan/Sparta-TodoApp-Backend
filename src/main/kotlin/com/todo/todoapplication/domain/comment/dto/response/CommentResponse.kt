package com.todo.todoapplication.domain.comment.dto.response

import com.todo.todoapplication.domain.comment.model.Comment

data class CommentResponse(
    val name: String,
    val content: String
) {
    companion object {
        fun from(comment: Comment): CommentResponse {
            return CommentResponse(
                name = comment.account.name,
                content = comment.content
            )
        }
    }
}