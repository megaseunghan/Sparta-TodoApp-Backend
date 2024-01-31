package com.todo.todoapplication.global.auth

import com.todo.todoapplication.domain.user.exception.USER_NOT_FOUND_MESSAGE
import com.todo.todoapplication.domain.user.model.User
import com.todo.todoapplication.domain.user.repository.UserRepository
import com.todo.todoapplication.global.exception.common.NoSuchEntityException
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.security.core.userdetails.User as SecurityUser

@Service
class CustomUserDetailService(
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails =
        userRepository.findByEmail(username)
            ?.let { createUserDetails(it) }
            ?: throw NoSuchEntityException(USER_NOT_FOUND_MESSAGE)

    private fun createUserDetails(user: User): UserDetails =
        SecurityUser(
            user.email,
            user.password,
            listOf(SimpleGrantedAuthority("ROLE_${user.role}"))
        )

}
