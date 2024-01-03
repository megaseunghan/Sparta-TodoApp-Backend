package com.todo.todoapplication.domain.todo.dto.request

import org.springframework.data.domain.Sort

data class TodoSortRequest(
    val direction: String = "desc",
    val criteria: String = "createdAt"
) {
    fun toSort(): Sort{
        return Sort.by(Sort.Direction.fromString(direction), criteria)
    }

}