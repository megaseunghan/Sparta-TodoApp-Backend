package com.todo.todoapplication.domain.comment.dto.request

import com.todo.todoapplication.domain.comment.model.Account
import com.todo.todoapplication.domain.comment.model.Comment
import com.todo.todoapplication.domain.todo.model.Todo
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class CommentCreateRequest(
    @field:NotBlank(message = "작성자는 필수 입력 사항입니다.")
    val name: String,

    @field:Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}${'$'}", message = "비밀번호는 영문과 특수문자 숫자를 포함하며 8자 이상이어야 합니다.")
    val password: String,

    @field:NotBlank(message = "댓글 내용은 필수 입력 사항입니다.")
    val content: String
) {

    fun toEntity(todo: Todo): Comment {
        return Comment(
            content = content,
            account = Account(
                name = name,
                password = password
            ),
            todo = todo
        )
    }

}
