package com.todo.todoapplication.domain.comment.repository

import com.todo.todoapplication.domain.comment.model.Comment
import com.todo.todoapplication.domain.todo.model.Todo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository : JpaRepository<Comment, Long> {
    fun findAllByTodo(todo: Todo): List<Comment>

}