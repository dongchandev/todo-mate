package com.todo.mate.server.domain.todo;


import com.todo.mate.server.enumeration.TodoStatus;

import java.time.LocalDate;

public class Todo {
    private final TodoId id;
    private Content content;
    private final DueDate dueDate;
    private TodoStatus status;


    private Todo(TodoId id, Content content, DueDate dueDate, TodoStatus status) {
        this.id = id;
        this.content = content;
        this.dueDate = dueDate;
        this.status = status;
    }

    public static Todo create(String content, LocalDate dueDate) {
        return new Todo(
                null,
                Content.of(content),
                DueDate.of(dueDate),
                TodoStatus.IN_PROGRESS
        );
    }

    public static Todo of(Long id, String content, LocalDate dueDate, TodoStatus status) {
        return new Todo(
                TodoId.of(id),
                Content.of(content),
                DueDate.of(dueDate),
                TodoStatus.IN_PROGRESS
        );
    }

    public TodoStatus getStatus() {
        return status;
    }

    public TodoId getId() {
        return id;
    }

    public Content getContent() {
        return content;
    }

    public DueDate getDueDate() {
        return dueDate;
    }
}
