package com.todo.todoapplication.domain.todo.service

import com.todo.todoapplication.domain.todo.dto.TodoCreationRequest
import com.todo.todoapplication.domain.todo.dto.TodoResponse
import com.todo.todoapplication.domain.todo.repository.TodoRepository
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
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

    // R
    fun getTodo(todoId: Long): TodoResponse {
        val todo = todoRepository.findByIdOrNull(todoId) ?: throw RuntimeException("$todoId 를 찾을 수 없습니다.}")
        return TodoResponse.from(todo)
    }

    fun getTodoList(): List<TodoResponse> {
        return todoRepository.findAll(Sort.by(Sort.Direction.DESC, "creation_time")).map {
            TodoResponse.from(it)
        }
    }
}