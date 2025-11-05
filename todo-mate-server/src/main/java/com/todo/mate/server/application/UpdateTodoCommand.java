package com.todo.mate.server.application;

public record UpdateTodoCommand(
        Long id,
        String content,
        String memo
) {
    public static UpdateTodoCommand of(Long id, String content, String memo) {
        return new UpdateTodoCommand(id, content, memo);
    }
}
