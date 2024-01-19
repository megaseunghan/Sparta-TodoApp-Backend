package com.todo.todoapplication.domain.comment.model

import com.todo.todoapplication.domain.comment.dto.request.CommentCreateRequest
import com.todo.todoapplication.domain.comment.dto.request.UpdateCommentRequest
import com.todo.todoapplication.domain.comment.dto.response.CommentResponse
import com.todo.todoapplication.domain.todo.model.Todo
import com.todo.todoapplication.domain.user.model.User
import com.todo.todoapplication.global.entity.BaseEntity
import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction

@Entity
@Table(name = "comments")
class Comment private constructor(
    content: String,
    todo: Todo,
    user: User
) : BaseEntity() {

    @Column(name = "content")
    var content = content
        private set

    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    var user: User = user
        private set

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "todo_id")
    var todo = todo
        private set

    fun update(request: UpdateCommentRequest) {
        this.content = request.content
    }

    fun from(): CommentResponse {
        return CommentResponse(
            email = this.user.email,
            content = this.content
        )
    }

    companion object {
        fun toEntity(request: CommentCreateRequest, todo: Todo, user: User): Comment {
            return Comment(
                content = request.content,
                todo = todo,
                user = user
            )
        }
    }

}
