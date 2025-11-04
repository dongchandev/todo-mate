package com.todo.mate.server.domain.todo;

public final class TodoId {
    private final Long value;

    private TodoId(Long value) {
        this.value = value;
    }

    public static TodoId of (Long id) {
        return new TodoId(id);
    }

    public Long value() {
        return value;
    }


}
