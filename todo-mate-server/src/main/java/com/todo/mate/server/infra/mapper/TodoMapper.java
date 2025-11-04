package com.todo.mate.server.infra.mapper;

import com.todo.mate.server.domain.todo.Todo;
import com.todo.mate.server.enumeration.TodoStatus;
import com.todo.mate.server.infra.db.TodoEntity;

public final class TodoMapper {

    private TodoMapper() {}

    public static TodoEntity toEntity(Todo todo) {
        if (todo == null) return null;
        return new TodoEntity(
                todo.getContent().value(),
                todo.getDueDate().value(),
                todo.getStatus() != null ? todo.getStatus() : TodoStatus.IN_PROGRESS
        );
    }

    public static Todo toDomain(TodoEntity entity) {
        if (entity == null) return null;
        return Todo.of(
                entity.getId(),
                entity.getContent(),
                entity.getDueDate(),
                entity.getStatus()
        );
    }
}
