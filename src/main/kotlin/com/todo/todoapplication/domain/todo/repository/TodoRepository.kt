package com.todo.todoapplication.domain.todo.repository

import com.todo.todoapplication.domain.todo.exception.TODO_NOT_FOUND_MESSAGE
import com.todo.todoapplication.domain.todo.model.Todo
import com.todo.todoapplication.global.exception.NoSuchEntityException
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
interface TodoRepository : JpaRepository<Todo, Long> {

    fun getTodoById(id: Long): Todo =
        this.findByIdOrNull(id) ?: throw NoSuchEntityException(TODO_NOT_FOUND_MESSAGE)

    fun findAllByName(name: String): List<Todo>

}
