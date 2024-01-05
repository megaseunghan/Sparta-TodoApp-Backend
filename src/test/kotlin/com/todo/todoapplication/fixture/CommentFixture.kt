package com.todo.todoapplication.fixture

import com.todo.todoapplication.domain.comment.dto.request.CommentCreateRequest
import com.todo.todoapplication.domain.comment.dto.request.UpdateCommentRequest
import com.todo.todoapplication.domain.comment.dto.response.CommentResponse

class CommentFixture {
    companion object {
        val commentId = 1L
        val wrongCommentId = 9999L

        val createCommentRequest = CommentCreateRequest(
            name = "홍길동",
            password = "ghdrlfehd1!",
            content = "댓글 내용"
        )

        val comments = listOf(
            CommentResponse("홍길동 1", "댓글 1"),
            CommentResponse("홍길동 2", "댓글 2"),
            CommentResponse("홍길동 3", "댓글 3")
        )

        val updateCommentRequest = UpdateCommentRequest(
            name = "홍길동",
            password = "ghdrlfehd1!",
            content = "댓글 내용 수정함"
        )

        val updateCommentRequestWithWrongIdAndPw = UpdateCommentRequest(
            name = "홍길동 아님",
            password = "ghdrlfehddksla1!",
            content = "댓글 내용 수정함"
        )

        val commentResponse = CommentResponse(
            name = "홍길동",
            content = "댓글 내용"
        )

        val updatedCommentResponse = CommentResponse(
            name = "홍길동",
            content = "댓글 내용 수정함"
        )

    }

}