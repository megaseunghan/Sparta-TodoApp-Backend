package com.todo.todoapplication.fixture

import com.todo.todoapplication.domain.todo.dto.request.TodoCreateRequest
import com.todo.todoapplication.domain.todo.dto.request.TodoUpdateRequest
import com.todo.todoapplication.domain.todo.dto.response.TodoResponse
import java.time.LocalDateTime

class TodoFixture {
    companion object {
        val todoId = 1L
        val wrongTodoId = 9999L

        val createTodoRequest = TodoCreateRequest(
            title = "투두 제목",
            description = "투두 내용",
            authorName = "홍길동",
        )

        val updateTodoRequest = TodoUpdateRequest(
            title = "업데이트 된 투두 제목",
            description = "업데이트 된 투두 내용",
            author = "홍길동에서 고길동"
        )

        val todoResponse = TodoResponse(
            id = 1L,
            title = "투두 제목",
            description = "투두 내용",
            createdAt = LocalDateTime.now(),
            author = "홍길동"
        )

        val todoResponseList: List<TodoResponse> = listOf(
            TodoResponse(1, "투두 제목 1", "투두 내용 1", LocalDateTime.now(), "홍길동"),
            TodoResponse(2, "투두 제목 2", "투두 내용 2", LocalDateTime.now(), "김철수"),
            TodoResponse(3, "투두 제목 3", "투두 내용 3", LocalDateTime.now(), "이나라")
        )
    }
}

