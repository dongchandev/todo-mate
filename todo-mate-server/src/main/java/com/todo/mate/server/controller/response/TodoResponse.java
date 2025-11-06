package com.todo.mate.server.controller.response;

import com.todo.mate.server.domain.entity.Todo;
import com.todo.mate.server.enumeration.TodoStatus;

import java.time.LocalDate;
import java.util.List;

public record TodoResponse(
        Long id,
        String text,
        LocalDate date,
        String memo,
        TodoStatus status
) {
    public static TodoResponse of(Todo todo) {
        return new TodoResponse(todo.getId(), todo.getContent(), todo.getDueDate(), todo.getMemo(), todo.getStatus());
    }

    public static List<TodoResponse> of(List<Todo> todos) {
        return todos.stream()
                .map(TodoResponse::of)
                .toList();
    }
}