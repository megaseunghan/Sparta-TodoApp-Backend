package com.todo.todoapplication.domain.todo.service

import com.todo.todoapplication.domain.comment.repository.CommentRepository
import com.todo.todoapplication.domain.todo.dto.request.TodoCreateRequest
import com.todo.todoapplication.domain.todo.dto.request.TodoSortRequest
import com.todo.todoapplication.domain.todo.dto.request.TodoUpdateRequest
import com.todo.todoapplication.domain.todo.dto.response.TodoResponse
import com.todo.todoapplication.domain.todo.repository.TodoRepository
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
        val todo = todoRepository.getTodoById(todoId)
        val comments = commentRepository.findAllByTodo(todo)
        return TodoResponse.from(todo, comments)
    }

    @Transactional(readOnly = true)
    fun getTodoList(request: TodoSortRequest): List<TodoResponse> {
        return todoRepository.findAll(request.toSort())
            .map { TodoResponse.from(it) }
    }

    @Transactional(readOnly = true)
    fun getTodoByName(name: String): List<TodoResponse> {
        return todoRepository.findAllByName(name).map {
            TodoResponse.from(it)
        }
    }

    // U
    @Transactional
    fun updateTodo(todoId: Long, request: TodoUpdateRequest) {
        val todo = todoRepository.getTodoById(todoId)
        todo.update(
            request.title,
            request.description,
            request.name
        )
    }

    @Transactional
    fun completeTodo(todoId: Long) {
        val todo = todoRepository.getTodoById(todoId)
        todo.toggleComplete()
    }

    // D
    @Transactional
    fun deleteTodo(todoId: Long) {
        val todo = todoRepository.getTodoById(todoId)
        todoRepository.delete(todo)
    }

}