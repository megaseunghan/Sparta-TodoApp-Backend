package com.todo.todoapplication.domain.todo.service

import com.todo.todoapplication.domain.todo.dto.request.TodoCreateRequest
import com.todo.todoapplication.domain.todo.dto.request.TodoUpdateRequest
import com.todo.todoapplication.domain.todo.dto.response.TodoResponse
import com.todo.todoapplication.domain.todo.repository.TodoRepository
import com.todo.todoapplication.global.exception.NoSuchEntityException
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TodoService(private val todoRepository: TodoRepository) {

    // C
    @Transactional
    fun createTodo(request: TodoCreateRequest): Long {
        val todo = todoRepository.save(request.toEntity())
        return todo.id!!
    }

    // R
    @Transactional(readOnly = true)
    fun getTodo(todoId: Long): TodoResponse {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw NoSuchEntityException(todoId)
        return TodoResponse.from(todo)
    }

    @Transactional(readOnly = true)
    fun getTodoList(): List<TodoResponse> {
        return todoRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt")).map {
            TodoResponse.from(it)
        }
    }

    // U
    @Transactional
    fun updateTodo(todoId: Long, request: TodoUpdateRequest) {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw NoSuchEntityException(todoId)

        todo.update(
            request.title,
            request.description,
            request.name
        )
    }

    @Transactional
    fun completeTodo(todoId: Long) {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw NoSuchEntityException(todoId)

        todo.toggleComplete()
    }

    // D
    @Transactional
    fun deleteTodo(todoId: Long) {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw NoSuchEntityException(todoId)
        todoRepository.delete(todo)
    }

}