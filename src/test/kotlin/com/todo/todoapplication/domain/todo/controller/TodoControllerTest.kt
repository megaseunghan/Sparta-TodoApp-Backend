package com.todo.todoapplication.domain.todo.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.todo.todoapplication.domain.todo.service.TodoService
import com.todo.todoapplication.fixture.TodoFixture.Companion.createTodoRequest
import com.todo.todoapplication.fixture.TodoFixture.Companion.todoResponse
import com.todo.todoapplication.fixture.TodoFixture.Companion.todoResponseList
import com.todo.todoapplication.fixture.TodoFixture.Companion.updateTodoRequest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.filter.CharacterEncodingFilter


@WebMvcTest(TodoController::class)
@AutoConfigureMockMvc
internal class TodoControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var todoService: TodoService

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @BeforeEach
    fun setUp(context: WebApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .addFilter<DefaultMockMvcBuilder?>(CharacterEncodingFilter("UTF-8", true))
            .build()
    }

    @Test
    internal fun `Todo가 생성된다`() {
        // given
        val todoId = 1L
        val request = createTodoRequest

        given(todoService.createTodo(request)).willReturn(todoId)

        // when & then
        mockMvc.perform(
            post("/api/v1/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpect {
            status().isCreated
        }
    }

    @Test
    internal fun `Todo 1개가 조회된다`() {

        // given
        val response = todoResponse

        given(todoService.getTodo(response.id)).willReturn(response)

        // when & then
        mockMvc.get("/api/v1/todos/{todoId}", response.id)
            .andExpectAll {
                status { isOk() }
                content().string(objectMapper.writeValueAsString(response))
            }
            .andDo { print() }
    }

    @Test
    internal fun `Todo 전체가 조회된다`() {
        // given
        val list = todoResponseList

        given(todoService.getTodoList()).willReturn(list)

        // when & then
        mockMvc.perform(
            get("/api/v1/todos")
        ).andExpect(content().string(objectMapper.writeValueAsString(list)))
            .andDo(print())
    }

    @Test
    internal fun `Todo가 업데이트된다`() {
        // given
        val request = updateTodoRequest
        val todoId = 1L
        willDoNothing().given(todoService).updateTodo(todoId, request)

        //  when & then
        mockMvc.perform(
            put("/api/v1/todos/{todoId}", todoId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isNoContent)
            .andDo(print())
    }

    @Test
    internal fun `Todo가 삭제된다`() {
        // given
        val deleteTodo = todoResponse

        // when & then
        doNothing().`when`(todoService).deleteTodo(deleteTodo.id)

        mockMvc.delete("/api/v1/todos/1")
            .andExpect {
                status { isNoContent() }
            }.andDo { print() }
    }
}