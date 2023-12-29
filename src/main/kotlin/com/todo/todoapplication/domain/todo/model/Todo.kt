package com.todo.todoapplication.domain.todo.model

import com.todo.todoapplication.global.entity.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "todos")
class Todo(
    title: String,
    description: String,
    author: String
) : BaseEntity() {

    @Column(name = "title")
    var title = title
        private set

    @Column(name = "description")
    var description = description
        private set

    @Column(name = "author")
    var author = author
        private set

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    fun update(title: String, description: String, author: String) {
        this.title = title
        this.description = description
        this.author = author
    }

}
