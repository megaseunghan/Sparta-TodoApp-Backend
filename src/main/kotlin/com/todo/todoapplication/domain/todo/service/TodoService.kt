package com.todo.todoapplication.domain.todo.service

import com.todo.todoapplication.domain.comment.dto.response.CommentResponse
import com.todo.todoapplication.domain.comment.repository.CommentRepository
import com.todo.todoapplication.domain.todo.dto.request.TodoCreateRequest
import com.todo.todoapplication.domain.todo.dto.request.TodoFilterByNameRequest
import com.todo.todoapplication.domain.todo.dto.request.TodoSortRequest
import com.todo.todoapplication.domain.todo.dto.request.TodoUpdateRequest
import com.todo.todoapplication.domain.todo.dto.response.TodoResponse
import com.todo.todoapplication.domain.todo.exception.TODO_NOT_FOUND_MESSAGE
import com.todo.todoapplication.domain.todo.repository.TodoRepository
import com.todo.todoapplication.global.exception.NoSuchEntityException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TodoService(
    private val todoRepository: TodoRepository,
    private val commentRepository: CommentRepository
) {

    // C
    @Transactional
    fun createTodo(request: TodoCreateRequest): Long {
        val todo = todoRepository.save(request.toEntity())
        return todo.id!!
    }

    // R
    @Transactional(readOnly = true)
    fun getTodo(todoId: Long): TodoResponse {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw NoSuchEntityException(TODO_NOT_FOUND_MESSAGE)
        val comments = commentRepository.findAllByTodo(todo)
            .map { CommentResponse.from(it) }
        return TodoResponse.from(todo, comments)
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
                .map { TodoResponse.from(it) }
        } else {
            todoRepository.findAll(sort)
                .map { TodoResponse.from(it) }
        }
    }

    // U
    @Transactional
    fun updateTodo(todoId: Long, request: TodoUpdateRequest) {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw NoSuchEntityException(TODO_NOT_FOUND_MESSAGE)
        todo.update(
            request.title,
            request.description,
            request.name
        )
    }

    @Transactional
    fun completeTodo(todoId: Long) {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw NoSuchEntityException(TODO_NOT_FOUND_MESSAGE)
        todo.toggleComplete()
    }

    // D
    @Transactional
    fun deleteTodo(todoId: Long) {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw NoSuchEntityException(TODO_NOT_FOUND_MESSAGE)
        todoRepository.delete(todo)
    }

}