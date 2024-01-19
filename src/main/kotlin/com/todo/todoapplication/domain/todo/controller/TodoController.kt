package com.todo.todoapplication.domain.todo.controller

import com.todo.todoapplication.domain.todo.dto.request.TodoCreateRequest
import com.todo.todoapplication.domain.todo.dto.request.TodoFilterByNameRequest
import com.todo.todoapplication.domain.todo.dto.request.TodoSortRequest
import com.todo.todoapplication.domain.todo.dto.request.TodoUpdateRequest
import com.todo.todoapplication.domain.todo.dto.response.TodoResponse
import com.todo.todoapplication.domain.todo.service.TodoService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/api/v1/todos")
class TodoController(private val todoService: TodoService) {

    // C
    @PostMapping
    fun createTodo(
        @Valid @RequestBody request: TodoCreateRequest,
        @AuthenticationPrincipal authenticated: User
    ): ResponseEntity<Unit> {
        val id = todoService.createTodo(request, authenticated)
        return ResponseEntity.created(URI.create(String.format("/api/v1/todos/%d", id))).build()
    }

    // R
    @GetMapping("/{todoId}")
    fun getTodo(@PathVariable todoId: Long): ResponseEntity<TodoResponse> {
        val response = todoService.getTodo(todoId)
        return ResponseEntity.ok(response)
    }

    @GetMapping
    fun getTodoList(
        todoSortRequest: TodoSortRequest,
        todoFilterByNameRequest: TodoFilterByNameRequest
    ): ResponseEntity<List<TodoResponse>> {
        val response = todoService.getTodoList(todoSortRequest, todoFilterByNameRequest)
        return ResponseEntity.ok(response)
    }

    // U
    @PutMapping("/{todoId}")
    fun updateTodo(
        @PathVariable todoId: Long,
        @Valid @RequestBody request: TodoUpdateRequest,
        @AuthenticationPrincipal authenticated: User
    ): ResponseEntity<Unit> {
        todoService.updateTodo(todoId, request, authenticated)
        return ResponseEntity.ok().build()
    }

    @PatchMapping("/{todoId}")
    fun completeTodo(
        @PathVariable todoId: Long,
        @AuthenticationPrincipal authenticated: User
    ): ResponseEntity<Unit> {
        todoService.completeTodo(todoId, authenticated)
        return ResponseEntity.ok().build()
    }

    // D
    @DeleteMapping("/{todoId}")
    fun deleteTodo(
        @PathVariable todoId: Long,
        @AuthenticationPrincipal authenticated: User
    ): ResponseEntity<Unit> {
        todoService.deleteTodo(todoId, authenticated)
        return ResponseEntity.noContent().build()
    }
}