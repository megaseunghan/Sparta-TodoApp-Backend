package com.todo.todoapplication.domain.todo.exception

class TodoNotFoundException(todoId: Long) : RuntimeException(
    "Todo 를 찾을 수 없습니다. todoID => $todoId"
)