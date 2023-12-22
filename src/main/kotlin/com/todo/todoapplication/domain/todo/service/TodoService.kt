package com.todo.todoapplication.domain.todo.service

import com.todo.todoapplication.domain.todo.dto.TodoCreationRequest
import com.todo.todoapplication.domain.todo.dto.TodoResponse
import com.todo.todoapplication.domain.todo.repository.TodoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TodoService(private val todoRepository: TodoRepository) {

    // C
    @Transactional
    fun addTodo(request: TodoCreationRequest): TodoResponse {
        val todo = todoRepository.save(request.toEntity())
        return TodoResponse.from(todo)
    }
}