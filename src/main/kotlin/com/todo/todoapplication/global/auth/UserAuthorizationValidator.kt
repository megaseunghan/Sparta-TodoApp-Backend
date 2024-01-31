package com.todo.todoapplication.global.auth

import com.todo.todoapplication.domain.user.repository.UserRepository
import org.springframework.security.core.userdetails.User as AuthenticatedUser
import com.todo.todoapplication.domain.user.model.User
import com.todo.todoapplication.global.exception.auth.AccessDeniedApiException

fun <T> isAuthorized(
    email: String,
    authenticated: AuthenticatedUser,
    userRepository: UserRepository,
    func: (user: User) -> T
): T {
    userRepository.findByEmail(email)
        ?.takeIf { it.email == authenticated.username }
        ?.let { return func.invoke(it) }
        ?: throw AccessDeniedApiException("API에 대한 접근 권한이 없습니다.")
}

fun <T> isAuthorized(user: User, authenticated: AuthenticatedUser, func: () -> T): T =
    authenticated.takeIf { it.username == user.email }.let { return func.invoke() }
