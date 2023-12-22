package com.todo.todoapplication.domain.todo.controller

import com.todo.todoapplication.domain.todo.dto.TodoCreationRequest
import com.todo.todoapplication.domain.todo.dto.TodoResponse
import com.todo.todoapplication.domain.todo.service.TodoService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController("/api/v1/todos")
class TodoController(private val todoService: TodoService) {

    // C
    @PostMapping
    fun addTodo(@RequestBody request: TodoCreationRequest): ResponseEntity<TodoResponse> {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(todoService.addTodo(request))
    }
}