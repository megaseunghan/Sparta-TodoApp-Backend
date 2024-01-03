package com.todo.todoapplication.domain.todo.service

import com.todo.todoapplication.fixture.TodoFixture.Companion.createTodoRequest
import com.todo.todoapplication.fixture.TodoFixture.Companion.todoId
import com.todo.todoapplication.fixture.TodoFixture.Companion.todoResponse
import com.todo.todoapplication.fixture.TodoFixture.Companion.todoResponseList
import com.todo.todoapplication.fixture.TodoFixture.Companion.updateTodoRequest
import com.todo.todoapplication.fixture.TodoFixture.Companion.wrongTodoId
import com.todo.todoapplication.global.exception.NoSuchEntityException
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class TodoServiceTest : BehaviorSpec() {

    private val todoService = mockk<TodoService>()

    init {
        // C
        Given("TodoCreateRequest가 주어졌을 때") {
            val request = createTodoRequest
            val todoId = todoId

            every { todoService.createTodo(any()) } returns todoId

            When("Validation에 맞는 올바른 Request라면") {
                Then("Todo가 저장된다") {
                    todoService.createTodo(request) shouldBe todoId
                }
            }
        }

        // R
        Given("조회를 위한 Todo Id가 주어졌을 때") {
            val todoId = todoId
            val wrongTodoId = wrongTodoId
            val response = todoResponse

            every { todoService.getTodo(any()) } returns response
            every { todoService.getTodo(wrongTodoId) } throws NoSuchEntityException(wrongTodoId)

            When("ID에 맞는 Todo가 존재한다면") {
                Then("Todo 1개가 조회된다") {
                    todoService.getTodo(todoId) shouldBe todoResponse
                }
            }

            When("ID에 맞는 Todo가 존재하지 않는다면") {
                val noSuchEntityException = shouldThrowExactly<NoSuchEntityException> {
                    todoService.getTodo(wrongTodoId)
                }

                Then("NoSuchTodoException 예외가 발생한다") {
                    noSuchEntityException::class.simpleName shouldBe "NoSuchTodoException"
                    noSuchEntityException.message shouldBe "Todo 를 찾을 수 없습니다. todoID => $wrongTodoId"
                }
            }
        }

        Given("모든 Todo를 조회했을 때") {
            val responseList = todoResponseList

            When("Todo가 존재한다면") {
                every { todoService.getTodoList() } returns responseList

                Then("모든 Todo가 조회된다") {
                    todoService.getTodoList() shouldBe responseList
                }
            }

            When("Todo가 존재하지 않는다면") {
                every { todoService.getTodoList() } returns emptyList()

                Then("비어있는 List가 리턴된다") {
                    todoService.getTodoList() shouldBe emptyList()
                }
            }
        }

        // U
        Given("TodoUpdateRequest가 주어졌을 때") {
            val todoId = todoId
            val request = updateTodoRequest

            every { todoService.updateTodo(any(), any()) } just runs

            When("Validation에 맞는 올바른 Request라면") {
                Then("Todo가 업데이트 된다") {
                    todoService.updateTodo(todoId, request) shouldBe Unit
                }
            }
        }

        Given("Todo 완료 처리 요청이 들어왔을 때") {
            val todoId = todoId
            var completed = false

            every { todoService.completeTodo(todoId) } answers {
                completed = !completed
            }

            When("Todo가 조회되면") {
                Then("Todo의 완료 여부가 토글된다.") {
                    todoService.completeTodo(todoId)
                    completed shouldBe true
                }
            }

        }

        // D
        Given("삭제를 위한 Todo Id가 주어졌을 때") {
            val todoId = todoId
            val wrongTodoId = wrongTodoId

            every { todoService.deleteTodo(any()) } just runs
            every { todoService.deleteTodo(wrongTodoId) } throws NoSuchEntityException(wrongTodoId)

            When("ID에 맞는 Todo가 존재한다면") {
                Then("해당 Todo가 삭제된다") {
                    todoService.deleteTodo(todoId) shouldBe Unit
                }
            }

            When("ID에 맞는 Todo가 존재하지 않는다면") {
                val noSuchEntityException = shouldThrowExactly<NoSuchEntityException> {
                    todoService.deleteTodo(wrongTodoId)
                }

                Then("NoSuchTodoException 예외가 발생한다") {
                    noSuchEntityException::class.simpleName shouldBe "NoSuchTodoException"
                    noSuchEntityException.message shouldBe "Todo 를 찾을 수 없습니다. todoID => $wrongTodoId"
                }
            }
        }
    }
}
