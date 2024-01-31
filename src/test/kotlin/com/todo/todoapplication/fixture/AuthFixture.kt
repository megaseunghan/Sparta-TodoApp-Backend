package com.todo.todoapplication.fixture

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User

class AuthFixture {
    companion object {
        val authenticatedUser = User("some@gmail.com", "1234", listOf(SimpleGrantedAuthority("ROLE_MEMBER")))
    }
}