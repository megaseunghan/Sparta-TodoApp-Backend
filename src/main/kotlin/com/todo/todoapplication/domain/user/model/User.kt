package com.todo.todoapplication.domain.user.model

import com.todo.todoapplication.domain.user.dto.request.SignupRequest
import jakarta.persistence.*

@Entity
@Table(name = "users")
class User private constructor(
    @Column(name = "email", unique = true)
    var email: String,

    @Column(name = "password")
    val password: String,

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    val role: UserRole
) {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
        private set

    companion object {
        fun toEntity(request: SignupRequest): User {
            return User(
                password = request.password,
                email = request.email,
                role = request.role
            )
        }
    }
}