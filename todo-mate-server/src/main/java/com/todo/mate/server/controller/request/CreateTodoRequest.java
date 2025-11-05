package com.todo.mate.server.controller.request;

import com.todo.mate.server.application.CreateTodoCommand;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateTodoRequest(
    @NotBlank String content,
    @NotNull @FutureOrPresent LocalDate dueDate,
    @NotNull String memo
) {
    public CreateTodoCommand toCommand() {
        return new CreateTodoCommand(content, dueDate);
    }
}