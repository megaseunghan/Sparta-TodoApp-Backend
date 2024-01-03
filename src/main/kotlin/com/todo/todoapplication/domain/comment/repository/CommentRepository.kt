package com.todo.todoapplication.domain.comment.repository

import com.todo.todoapplication.domain.comment.exception.COMMENT_NOT_FOUND_MESSAGE
import com.todo.todoapplication.domain.comment.model.Comment
import com.todo.todoapplication.global.exception.NoSuchEntityException
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository : JpaRepository<Comment, Long> {
    fun findAllByTodoId(todoId: Long): List<Comment>
    fun getCommentById(commentId: Long): Comment =
        this.findByIdOrNull(commentId) ?: throw NoSuchEntityException(COMMENT_NOT_FOUND_MESSAGE)
}