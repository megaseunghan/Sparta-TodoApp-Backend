package com.todo.todoapplication.domain.todo.dto

import com.todo.todoapplication.fixture.TodoFixture.Companion.createTodoRequest
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.BehaviorSpec
import org.springframework.web.bind.MethodArgumentNotValidException

class TodoCreateRequestTest : BehaviorSpec({
    Given("TodoCreationRequest가 생성되었을 때") {
        When("제목이 입력되지 않으면") {
            Then("InvalidTodoRequestException이 발생한다") {
                shouldThrowExactly<MethodArgumentNotValidException> { createTodoRequest }
            }
        }
        When("작성자가 입력되지 않으면") {
            Then("InvalidTodoRequestException이 발생한다") {
                shouldThrowExactly<MethodArgumentNotValidException> { createTodoRequest }
            }
        }
    }
})
