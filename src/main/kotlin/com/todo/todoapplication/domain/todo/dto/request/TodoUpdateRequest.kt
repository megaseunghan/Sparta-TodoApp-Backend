package com.todo.todoapplication.domain.todo.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class TodoUpdateRequest(
    @field:Size(min = 1, max = 200, message = "제목은 1글자 이상 200글자 이내로 작성해주세요.")
    val title: String,

    @field:Size(min = 1, max = 1000, message = "할 일 본문은 1글자 이상 1,000글자 이내로 작성해주세요.")
    val description: String,

    @field:NotBlank(message = "작성자는 필수 입력 사항입니다.")
    val name: String,
)