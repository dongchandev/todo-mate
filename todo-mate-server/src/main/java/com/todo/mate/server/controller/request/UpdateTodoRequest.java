package com.todo.mate.server.controller.request;

import com.todo.mate.server.application.UpdateTodoCommand;

public record UpdateTodoRequest(
        String content,
        String memo
) {
    public UpdateTodoCommand toCommand(Long id) {
        return new UpdateTodoCommand(id, content, memo);
    }
}
