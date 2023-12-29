package com.todo.todoapplication.global.exception.todo

class NoSuchTodoException(todoId: Long) : RuntimeException(
    "Todo 를 찾을 수 없습니다. todoID => $todoId"
)