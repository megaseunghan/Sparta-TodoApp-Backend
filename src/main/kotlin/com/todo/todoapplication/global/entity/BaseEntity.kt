package com.todo.todoapplication.global.entity

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity {

    @Column(columnDefinition = "TIMESTAMP(6)", nullable = false, updatable = false)
    @CreatedDate
    lateinit var createdAt: LocalDateTime
        protected set

    @Column(columnDefinition = "TIMESTAMP(6)", nullable = false)
    @LastModifiedDate
    lateinit var updatedAt: LocalDateTime
        protected set

}