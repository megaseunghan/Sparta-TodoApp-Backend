package com.todo.todoapplication.domain.todo.repository

import com.todo.todoapplication.domain.todo.model.Todo
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TodoRepository : JpaRepository<Todo, Long> {

    fun findAllByName(name: String, sort: Sort): List<Todo>

}
