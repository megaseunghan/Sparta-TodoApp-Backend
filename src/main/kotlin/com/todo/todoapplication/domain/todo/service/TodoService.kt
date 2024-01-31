package com.todo.todoapplication.domain.todo.service

import com.todo.todoapplication.domain.comment.repository.CommentRepository
import com.todo.todoapplication.domain.todo.dto.request.TodoCreateRequest
import com.todo.todoapplication.domain.todo.dto.request.TodoFilterByNameRequest
import com.todo.todoapplication.domain.todo.dto.request.TodoSortRequest
import com.todo.todoapplication.domain.todo.dto.request.TodoUpdateRequest
import com.todo.todoapplication.domain.todo.dto.response.TodoResponse
import com.todo.todoapplication.domain.todo.exception.TODO_NOT_FOUND_MESSAGE
import com.todo.todoapplication.domain.todo.model.Todo
import com.todo.todoapplication.domain.todo.repository.TodoRepository
import com.todo.todoapplication.domain.user.repository.UserRepository
import com.todo.todoapplication.global.auth.isAuthorized
import com.todo.todoapplication.global.exception.common.NoSuchEntityException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TodoService(
    private val todoRepository: TodoRepository,
    private val userRepository: UserRepository,
    private val commentRepository: CommentRepository
) {


    // C
    @Transactional
    fun createTodo(request: TodoCreateRequest, authenticated: User) =
        isAuthorized(request.email, authenticated, userRepository) {
            todoRepository.save(Todo.toEntity(request, it)).id!!
        }

    // R
    @Transactional(readOnly = true)
    fun getTodo(todoId: Long): TodoResponse {
        val todo = getTodoById(todoId)
        val comments = commentRepository.findAllByTodo(todo)
            .map { it.from() }
        return todo.from(comments)
    }

    @Transactional(readOnly = true)
    fun getTodoList(
        todoSortRequest: TodoSortRequest,
        todoFilterByNameRequest: TodoFilterByNameRequest
    ): List<TodoResponse> {
        val name = todoFilterByNameRequest.name
        val sort = todoSortRequest.toSort()

        return if (name.isNotBlank()) {
            todoRepository.findAllByName(name, sort)
                .map { it.from() }
        } else {
            todoRepository.findAll(sort)
                .map { it.from() }
        }
    }

    // U
    @Transactional
    fun updateTodo(todoId: Long, request: TodoUpdateRequest, authenticated: User) {
        val todo = getTodoById(todoId)

        isAuthorized(todo.user, authenticated) {
            todo.update(
                request.title,
                request.description,
                request.name
            )
        }
    }

    @Transactional
    fun completeTodo(todoId: Long, authenticated: User) {
        val todo = getTodoById(todoId)

        isAuthorized(todo.user, authenticated) {
            todo.toggleComplete()
        }
    }

    // D
    @Transactional
    fun deleteTodo(todoId: Long, authenticated: User) {
        val todo = getTodoById(todoId)

        isAuthorized(todo.user, authenticated) {
            todoRepository.delete(todo)
        }
    }

    fun getTodoById(todoId: Long): Todo {
        return todoRepository.findByIdOrNull(todoId) ?: throw NoSuchEntityException(TODO_NOT_FOUND_MESSAGE)
    }

}
