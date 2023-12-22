package com.todo.todoapplication.domain.todo.model

import com.todo.todoapplication.domain.todo.dto.TodoResponse
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Todo(
    @Column(name = "title")
    var title: String,

    @Column(name = "description")
    var description: String,

    @Column(name = "creation_time")
    val creationTime: LocalDateTime = LocalDateTime.now(),

    @Column(name = "author")
    var author: String
) {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}