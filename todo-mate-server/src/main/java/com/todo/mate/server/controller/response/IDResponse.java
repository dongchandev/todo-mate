package com.todo.mate.server.controller.response;

public record IDResponse(
        Long id
) {
    public static IDResponse of(Long id) {
        return new IDResponse(id);
    }
}
