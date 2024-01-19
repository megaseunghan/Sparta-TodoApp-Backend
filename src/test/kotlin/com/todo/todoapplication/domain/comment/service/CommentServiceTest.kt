package com.todo.todoapplication.domain.comment.service

import com.todo.todoapplication.domain.comment.exception.COMMENT_NOT_FOUND_MESSAGE
import com.todo.todoapplication.domain.todo.exception.TODO_NOT_FOUND_MESSAGE
import com.todo.todoapplication.fixture.AuthFixture.Companion.authenticatedUser
import com.todo.todoapplication.fixture.CommentFixture.Companion.commentId
import com.todo.todoapplication.fixture.CommentFixture.Companion.comments
import com.todo.todoapplication.fixture.CommentFixture.Companion.createCommentRequest
import com.todo.todoapplication.fixture.CommentFixture.Companion.updateCommentRequest
import com.todo.todoapplication.fixture.CommentFixture.Companion.updateCommentRequestWithWrongIdAndPw
import com.todo.todoapplication.fixture.CommentFixture.Companion.updatedCommentResponse
import com.todo.todoapplication.fixture.CommentFixture.Companion.wrongCommentId
import com.todo.todoapplication.fixture.TodoFixture.Companion.todoId
import com.todo.todoapplication.fixture.TodoFixture.Companion.wrongTodoId
import com.todo.todoapplication.global.exception.IdPasswordMismatchException
import com.todo.todoapplication.global.exception.NoSuchEntityException
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs

class CommentServiceTest : BehaviorSpec({
    val commentService = mockk<CommentService>()

    Given("댓글을 작성할 때") {

        every { commentService.createComment(any(), createCommentRequest, any()) } returns 1
        every { commentService.createComment(wrongTodoId, createCommentRequest, any()) } throws NoSuchEntityException(
            TODO_NOT_FOUND_MESSAGE
        )

        When("todoId로 조회되는 Todo가 없다면") {
            val exception = shouldThrowExactly<NoSuchEntityException> {
                commentService.createComment(wrongTodoId, createCommentRequest, authenticatedUser)
            }

            Then("생성되지 않고 예외가 발생해야 한다.") {
                exception.message shouldBe TODO_NOT_FOUND_MESSAGE
            }
        }
        When("todoId로 조회되는 Todo가 있다면") {
            Then("댓글이 생성된다") {
                commentService.createComment(todoId, createCommentRequest, authenticatedUser) shouldBe 1
            }
        }
    }

    Given("댓글을 조회할 때") {

        every { commentService.findAll(any()) } returns comments
        every { commentService.findAll(wrongTodoId) } throws NoSuchEntityException(
            TODO_NOT_FOUND_MESSAGE
        )
        When("todoId로 조회되는 Todo가 없다면") {
            val exception = shouldThrowExactly<NoSuchEntityException> {
                commentService.findAll(wrongTodoId)
            }

            Then("예외가 발생해야 한다.") {
                exception.message shouldBe TODO_NOT_FOUND_MESSAGE
            }
        }
        When("todoId로 조회되는 Todo가 있다면") {
            Then("댓글 리스트가 조회된다.") {
                commentService.findAll(todoId) shouldBe comments
            }
        }
    }

    Given("댓글을 수정할 때") {

        every { commentService.updateComment(any(), updateCommentRequest, any()) } returns updatedCommentResponse
        every {
            commentService.updateComment(
                wrongCommentId,
                updateCommentRequest,
                any()
            )
        } throws NoSuchEntityException(
            COMMENT_NOT_FOUND_MESSAGE
        )
        every {
            commentService.updateComment(
                commentId,
                updateCommentRequestWithWrongIdAndPw,
                authenticatedUser
            )
        } throws IdPasswordMismatchException("아이디와 패스워드가 일치하지 않습니다.")

        When("commentId로 조회되는 댓글이 없다면") {
            val exception = shouldThrowExactly<NoSuchEntityException> {
                commentService.updateComment(wrongCommentId, updateCommentRequest, authenticatedUser)
            }
            Then("예외가 발생한다") {
                exception.message shouldBe COMMENT_NOT_FOUND_MESSAGE
            }
        }
        When("ID와 패스워드가 일치하지 않다면") {
            val exception = shouldThrowExactly<IdPasswordMismatchException> {
                commentService.updateComment(commentId, updateCommentRequestWithWrongIdAndPw, authenticatedUser)
            }
            Then("예외가 발생한다") {
                exception.message shouldBe "아이디와 패스워드가 일치하지 않습니다."
            }
        }
        When("commentId로 조회되는 댓글이 있고 ID와 패스워드가 일치하면") {
            Then("댓글이 수정된다.") {
                val response = commentService.updateComment(commentId, updateCommentRequest, authenticatedUser)
                response.content shouldBe updatedCommentResponse.content
            }
        }
    }

    Given("댓글을 삭제할 때") {
        every { commentService.deleteComment(any(), any()) } just runs
        every { commentService.deleteComment(wrongCommentId, any()) } throws NoSuchEntityException(
            COMMENT_NOT_FOUND_MESSAGE
        )

        When("commentId로 조회되는 댓글이 없으면") {
            val exception = shouldThrowExactly<NoSuchEntityException> {
                commentService.deleteComment(wrongCommentId, authenticatedUser)
            }
            Then("예외가 발생한다.") {
                exception.message shouldBe COMMENT_NOT_FOUND_MESSAGE
            }
        }
        When("commentId로 조회되는 댓글이 있으면") {
            Then("댓글이 삭제된다") {
                commentService.deleteComment(commentId, authenticatedUser) shouldBe Unit
            }
        }
    }
})