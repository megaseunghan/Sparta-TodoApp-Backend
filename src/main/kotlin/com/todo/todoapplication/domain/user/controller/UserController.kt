package com.todo.todoapplication.domain.user.controller

import com.todo.todoapplication.domain.user.dto.request.LoginRequest
import com.todo.todoapplication.domain.user.dto.request.SignupRequest
import com.todo.todoapplication.domain.user.dto.response.LoginResponse
import com.todo.todoapplication.domain.user.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("api/v1/users/")
class UserController(
    private val userService: UserService
) {

    @PostMapping("/signup")
    fun signup(@RequestBody request: SignupRequest): ResponseEntity<Unit> {
        val userId = userService.signup(request)
        return ResponseEntity.created(URI.create(String.format("/api/v1/%d", userId))).build()
    }

    @PostMapping("/signin")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<LoginResponse> {
        val tokenInfo = userService.login(request)

        return ResponseEntity.ok(tokenInfo)
    }

}