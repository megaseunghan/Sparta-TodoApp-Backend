package com.todo.todoapplication.fixture

import org.springframework.security.core.userdetails.User

class AuthFixture {
    companion object {
        val authenticatedUser = User("", "", null)
    }
}