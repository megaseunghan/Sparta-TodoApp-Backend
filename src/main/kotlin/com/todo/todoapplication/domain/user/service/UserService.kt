package com.todo.todoapplication.domain.user.service

import com.todo.todoapplication.domain.user.dto.request.LoginRequest
import com.todo.todoapplication.domain.user.dto.request.SignupRequest
import com.todo.todoapplication.domain.user.dto.response.LoginResponse
import com.todo.todoapplication.domain.user.exception.USER_NOT_FOUND_MESSAGE
import com.todo.todoapplication.domain.user.model.User
import com.todo.todoapplication.domain.user.repository.UserRepository
import com.todo.todoapplication.global.auth.JwtTokenProvider
import com.todo.todoapplication.global.exception.member.DuplicatedEmailException
import com.todo.todoapplication.global.exception.common.NoSuchEntityException
import org.springframework.stereotype.Service

@Service
class UserService(
    private val jwtTokenProvider: JwtTokenProvider,
    private val userRepository: UserRepository
) {
    fun signup(request: SignupRequest): Long {
        if (userRepository.existsByEmail(request.email)) {
            throw DuplicatedEmailException("해당 이메일로 가입된 사용자가 있습니다.")
        }

        val user = User.toEntity(request)
        userRepository.save(user)

        return user.id!!
    }

    fun login(request: LoginRequest): LoginResponse {
        val user = userRepository.findByEmail(request.email) ?: throw NoSuchEntityException(USER_NOT_FOUND_MESSAGE)

        return jwtTokenProvider.generateAccessToken(user)
    }

}
