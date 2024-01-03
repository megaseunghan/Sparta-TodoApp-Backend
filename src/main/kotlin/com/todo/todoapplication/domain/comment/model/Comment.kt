package com.todo.todoapplication.domain.comment.model

import com.todo.todoapplication.domain.comment.dto.request.UpdateCommentRequest
import com.todo.todoapplication.domain.todo.model.Todo
import com.todo.todoapplication.global.entity.BaseEntity
import com.todo.todoapplication.global.exception.InvalidRequestArgumentException
import com.todo.todoapplication.global.exception.message.NAME_PASSWORD_NOT_MATCHING
import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction

@Entity
@Table(name = "comments")
class Comment(
    @Embedded
    val account: Account,
    content: String,
    todo: Todo,
) : BaseEntity() {

    @Column(name = "comment")
    var content = content
        private set

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "todo_id")
    var todo = todo
        private set

    fun update(request: UpdateCommentRequest) {
        if (this.account.name == request.name && this.account.password == request.password) {
            this.content = request.content
        }

        throw InvalidRequestArgumentException(NAME_PASSWORD_NOT_MATCHING)
    }
}
