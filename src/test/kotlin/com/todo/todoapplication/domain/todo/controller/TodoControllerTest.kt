package com.todo.todoapplication.domain.todo.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.todo.todoapplication.domain.todo.dto.TodoCreationRequest
import com.todo.todoapplication.domain.todo.dto.TodoResponse
import com.todo.todoapplication.domain.todo.dto.TodoUpdateRequest
import com.todo.todoapplication.domain.todo.service.TodoService
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.doNothing
import org.mockito.BDDMockito.doReturn
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime

@WebMvcTest(TodoController::class)
@AutoConfigureMockMvc
internal class TodoControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var todoService: TodoService

    @Test
    internal fun `Todo가 생성된다`() {
        // given
        val request = TodoCreationRequest(
            title = "투두 제목",
            description = "투두 내용",
            author = "홍길동",
        )

        val jsonBody = jacksonObjectMapper().writeValueAsString(request)

        // when & then
        doReturn(
            TodoResponse(
                title = request.title,
                description = request.description,
                author = request.author,
                creationTime = LocalDateTime.now()
            )
        ).`when`(todoService).addTodo(request)

        mockMvc.perform(
            post("/api/v1/todos").content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.title").value("투두 제목"))
            .andExpect(jsonPath("$.description").value("투두 내용"))
            .andExpect(jsonPath("$.author").value("홍길동"))
            .andDo(print())

    }

    @Test
    internal fun `Todo 1개가 조회된다`() {

        // given
        val response = TodoResponse(
            title = "투두 제목",
            description = "투두 내용",
            author = "홍길동",
            creationTime = LocalDateTime.now()
        )

        // when & then
        doReturn(response)
            .`when`(todoService).getTodo(1)

        mockMvc.get("/api/v1/todos/1")
            .andExpectAll {
                status { isOk() }
                jsonPath("$.title").exists()
                jsonPath("$.description").exists()
                jsonPath("$.creationTime").exists()
                jsonPath("$.author").exists()
            }
            .andDo { print() }
    }

    @Test
    internal fun `Todo 전체가 조회된다`() {
        // given
        val todos: List<TodoResponse> = listOf(
            TodoResponse("투두 제목 1", "투두 내용 1", LocalDateTime.now(), "홍길동"),
            TodoResponse("투두 제목 2", "투두 내용 2", LocalDateTime.now(), "김철수"),
            TodoResponse("투두 제목 3", "투두 내용 3", LocalDateTime.now(), "이나라")
        )

        // when & then
        doReturn(todos)
            .`when`(todoService).getTodoList()

        mockMvc.get("/api/v1/todos")
            .andExpectAll {
                jsonPath("$.length()").value(3)
                jsonPath("$[0].title").value("투두 제목 1")
                jsonPath("$[0].description").value("투두 내용 1")
                jsonPath("$[0].author").value("홍길동")

                jsonPath("$[0].title").value("투두 제목 2")
                jsonPath("$[0].description").value("투두 내용 2")
                jsonPath("$[0].author").value("김철수")

                jsonPath("$[0].title").value("투두 제목 3")
                jsonPath("$[0].description").value("투두 내용 3")
                jsonPath("$[0].author").value("이나라")
            }
            .andDo { print() }
    }

    @Test
    internal fun `Todo가 업데이트된다`() {
        // given
        val request = TodoUpdateRequest(
            title = "업데이트 된 투두 제목",
            description = "업데이트된 투두 내용",
            author = "이나라"
        )

        val jsonBody = jacksonObjectMapper().writeValueAsString(request)

        val findTodo = TodoResponse(
            title = "투두 제목",
            description = "투두 내용",
            author = "홍길동",
            creationTime = LocalDateTime.now()
        )

        val updateTodo = TodoResponse(
            title = "업데이트 된 투두 제목",
            description = "업데이트된 투두 내용",
            author = "홍길동",
            creationTime = LocalDateTime.now()
        )

        // when & then
        doReturn(findTodo)
            .`when`(todoService).getTodo(1)

        doReturn(updateTodo)
            .`when`(todoService).updateTodo(1, request)

        mockMvc.perform(
            put("/api/v1/todos/1")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.title").value(updateTodo.title))
            .andExpect(jsonPath("$.description").value(updateTodo.description))
            .andExpect(jsonPath("$.author").value(updateTodo.author))
            .andDo(print())
    }

    @Test
    internal fun `Todo가 삭제된다`() {
        // given
        val deleteTodo = TodoResponse(
            title = "투두 제목",
            description = "투두 내용",
            author = "홍길동",
            creationTime = LocalDateTime.now()
        )

        // when & then
        doReturn(deleteTodo)
            .`when`(todoService).getTodo(1)

        doNothing()
            .`when`(todoService).deleteTodo(1)

        mockMvc.delete("/api/v1/todos/1")
            .andExpect {
                status { isNoContent() }
            }.andDo { print() }
    }
}