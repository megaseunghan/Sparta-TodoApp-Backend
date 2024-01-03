package com.todo.todoapplication.domain.todo.model

import com.todo.todoapplication.global.entity.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "todos")
class Todo(
    title: String, description: String, name: String
) : BaseEntity() {

    @Column(name = "title")
    var title = title
        private set

    @Column(name = "description")
    var description = description
        private set

    @Column(name = "author")
    var name = name
        private set

    @Column(name = "complete")
    var completed = false
        private set

    @Id
    @Column(name = "todo_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    fun update(title: String, description: String, author: String) {
        this.title = title
        this.description = description
        this.name = author
    }

    fun toggleComplete() {
        this.completed = !completed
    }

}
