package com.todo.mate.server.application;

import java.time.LocalDate;

public record CreateTodoCommand(
        String content,
        LocalDate dueDate
) {
    public static CreateTodoCommand of(String content, LocalDate dueDate) {
        return new CreateTodoCommand(content, dueDate);
    }
}
