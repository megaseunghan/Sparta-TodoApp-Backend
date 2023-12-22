package com.todo.todoapplication.domain.todo.model

import java.time.LocalDateTime

class Todo(
    var title: String,
    var description: String,
    val creationTime: LocalDateTime,
    var author: String
) {
    val id: Long? = null
}