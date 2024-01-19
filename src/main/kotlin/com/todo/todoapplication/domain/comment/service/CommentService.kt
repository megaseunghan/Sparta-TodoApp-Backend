package com.todo.todoapplication.domain.comment.service

import com.todo.todoapplication.domain.comment.dto.request.CommentCreateRequest
import com.todo.todoapplication.domain.comment.dto.request.UpdateCommentRequest
import com.todo.todoapplication.domain.comment.dto.response.CommentResponse
import com.todo.todoapplication.domain.comment.exception.COMMENT_NOT_FOUND_MESSAGE
import com.todo.todoapplication.domain.comment.model.Comment
import com.todo.todoapplication.domain.comment.repository.CommentRepository
import com.todo.todoapplication.domain.todo.service.TodoService
import com.todo.todoapplication.domain.user.repository.UserRepository
import com.todo.todoapplication.global.auth.validate.isAuthorized
import com.todo.todoapplication.global.exception.IdPasswordMismatchException
import com.todo.todoapplication.global.exception.NoSuchEntityException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentService(
    private val userRepository: UserRepository,
    private val commentRepository: CommentRepository,
    private val todoService: TodoService,
    private val passwordEncoder: PasswordEncoder
) {

    // C
    @Transactional
    fun createComment(todoId: Long, request: CommentCreateRequest, authenticated: User): Long {
        val todo = todoService.getTodoById(todoId)

        return isAuthorized(request.email, authenticated, userRepository) {
            val comment = Comment.toEntity(request = request, todo = todo, user = it)
            commentRepository.save(comment).id!!
        }
    }

    // R
    @Transactional(readOnly = true)
    fun findAll(todoId: Long): List<CommentResponse> {
        val todo = todoService.getTodoById(todoId)
        val comments = commentRepository.findAllByTodo(todo)

        return comments.map { it.from() }
    }

    // U
    @Transactional
    fun updateComment(commentId: Long, request: UpdateCommentRequest, authenticated: User): CommentResponse {
        val comment = getCommentById(commentId)

        return isAuthorized(request.email, authenticated, userRepository) {
            if (request.email == it.email && passwordEncoder.matches(request.password, it.password)) {
                comment.update(request)
                comment.from()
            } else {
                throw IdPasswordMismatchException("아이디와 패스워드가 일치하지 않습니다.")
            }
        }
    }

    // D
    @Transactional
    fun deleteComment(commentId: Long, authenticated: User) {
        val comment = getCommentById(commentId)

        return isAuthorized(comment.user, authenticated) {
            commentRepository.delete(comment)
        }
    }

    fun getCommentById(commentId: Long): Comment {
        return commentRepository.findByIdOrNull(commentId) ?: throw NoSuchEntityException(COMMENT_NOT_FOUND_MESSAGE)
    }
}