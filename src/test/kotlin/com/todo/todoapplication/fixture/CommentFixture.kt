package com.todo.todoapplication.fixture

import com.todo.todoapplication.domain.comment.dto.request.CommentCreateRequest
import com.todo.todoapplication.domain.comment.dto.request.UpdateCommentRequest
import com.todo.todoapplication.domain.comment.dto.response.CommentResponse

class CommentFixture {
    companion object {
        const val commentId = 1L
        const val wrongCommentId = 9999L

        val createCommentRequest = CommentCreateRequest(
            email = "홍길동",
            password = "ghdrlfehd1!",
            content = "댓글 내용"
        )

        val comments = listOf(
            CommentResponse("홍길동 1", "댓글 1"),
            CommentResponse("홍길동 2", "댓글 2"),
            CommentResponse("홍길동 3", "댓글 3")
        )

        val updateCommentRequest = UpdateCommentRequest(
            email = "홍길동",
            password = "ghdrlfehd1!",
            content = "댓글 내용 수정함"
        )

        val updateCommentRequestWithWrongIdAndPw = UpdateCommentRequest(
            email = "홍길동 아님",
            password = "ghdrlfehddksla1!",
            content = "댓글 내용 수정함"
        )

        val commentResponse = CommentResponse(
            email = "홍길동",
            content = "댓글 내용"
        )

        val updatedCommentResponse = CommentResponse(
            email = "홍길동",
            content = "댓글 내용 수정함"
        )

    }

}