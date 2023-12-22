package com.todo.todoapplication.domain.todo.repository

import com.todo.todoapplication.domain.todo.model.Todo
import org.springframework.data.jpa.repository.JpaRepository

interface TodoRepository : JpaRepository<Todo, Long> {
}
