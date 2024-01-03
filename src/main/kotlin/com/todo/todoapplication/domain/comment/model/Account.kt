package com.todo.todoapplication.domain.comment.model

import jakarta.persistence.Embeddable

@Embeddable
data class Account(
    val name: String,
    val password: String
)