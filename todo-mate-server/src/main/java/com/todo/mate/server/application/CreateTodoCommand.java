package com.todo.mate.server.application;

import java.time.LocalDate;

public record CreateTodoCommand(
        String content,
        LocalDate dueDate,
        String memo
) {
    public static CreateTodoCommand of(String content, LocalDate dueDate, String memo) {
        return new CreateTodoCommand(content, dueDate,memo);
    }
}
