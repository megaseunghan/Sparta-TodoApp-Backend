package com.todo.todoapplication.global.exception

class NoSuchEntityException(id: Long) : RuntimeException(
    "Entity 을/를 찾을 수 없습니다. ID => $id"
)