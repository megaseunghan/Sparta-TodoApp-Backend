package com.todo.todoapplication.domain.comment.service

import com.todo.todoapplication.domain.comment.dto.request.CommentCreateRequest
import com.todo.todoapplication.domain.comment.dto.request.UpdateCommentRequest
import com.todo.todoapplication.domain.comment.dto.response.CommentResponse
import com.todo.todoapplication.domain.comment.exception.COMMENT_NOT_FOUND_MESSAGE
import com.todo.todoapplication.domain.comment.repository.CommentRepository
import com.todo.todoapplication.domain.todo.exception.TODO_NOT_FOUND_MESSAGE
import com.todo.todoapplication.domain.todo.repository.TodoRepository
import com.todo.todoapplication.global.exception.NoSuchEntityException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val todoRepository: TodoRepository
) {

    // C
    @Transactional
    fun createComment(todoId: Long, request: CommentCreateRequest): Long {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw NoSuchEntityException(TODO_NOT_FOUND_MESSAGE)
        val comment = commentRepository.save(request.toEntity(todo))

        return comment.id!!
    }

    // R
    @Transactional(readOnly = true)
    fun findAll(todoId: Long): List<CommentResponse> {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw NoSuchEntityException(TODO_NOT_FOUND_MESSAGE)
        val comments = commentRepository.findAllByTodo(todo)

        return comments.map { CommentResponse.from(it) }
    }

    // U
    @Transactional
    fun updateComment(commentId: Long, request: UpdateCommentRequest): CommentResponse {
        val comment = commentRepository.findByIdOrNull(commentId) ?: throw NoSuchEntityException(
            COMMENT_NOT_FOUND_MESSAGE
        )

        comment.update(request)

        return CommentResponse.from(comment)
    }

    // D
    @Transactional
    fun deleteComment(commentId: Long) {
        val comment = commentRepository.findByIdOrNull(commentId) ?: throw NoSuchEntityException(COMMENT_NOT_FOUND_MESSAGE)

        commentRepository.delete(comment)
    }

}