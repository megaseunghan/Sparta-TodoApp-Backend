package com.todo.todoapplication.domain.todo.model

import com.todo.todoapplication.domain.comment.dto.response.CommentResponse
import com.todo.todoapplication.domain.todo.dto.request.TodoCreateRequest
import com.todo.todoapplication.domain.todo.dto.response.TodoResponse
import com.todo.todoapplication.domain.user.model.User
import com.todo.todoapplication.global.entity.BaseEntity
import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction

@Entity
@Table(name = "todos")
class Todo private constructor(
    @Column(name = "title")
    private var title: String,

    @Column(name = "description")
    private var description: String,

    @Column(name = "name")
    private var name: String,

    user: User
) : BaseEntity() {

    @Id
    @Column(name = "todo_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    var user = user
        private set

    @Column(name = "complete")
    private var completed: Boolean = false

    fun update(title: String, description: String, author: String) {
        this.title = title
        this.description = description
        this.name = author
    }

    fun toggleComplete() {
        this.completed = !completed
    }

    fun from(): TodoResponse {
        return TodoResponse(
            this.id!!,
            this.title,
            this.description,
            this.createdAt,
            this.updatedAt,
            this.name,
            this.completed
        )
    }

    fun from(comments: List<CommentResponse>): TodoResponse {
        return TodoResponse(
            this.id!!,
            this.title,
            this.description,
            this.createdAt,
            this.updatedAt,
            this.name,
            this.completed,
            comments = comments
        )
    }

    companion object {

        fun toEntity(request: TodoCreateRequest, user: User): Todo {
            return Todo(
                title = request.title,
                description = request.description,
                name = request.name,
                user = user
            )

        }
    }
}
