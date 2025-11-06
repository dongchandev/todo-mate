package com.todo.mate.server.infra.db;

import com.todo.mate.server.enumeration.TodoStatus;
import jakarta.persistence.*;

import java.time.LocalDate;


@Entity(name = "tb_todo")
public class TodoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    private TodoStatus status;

    public Long getId() {
        return id;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public String getContent() {
        return content;
    }

    public TodoStatus getStatus() {
        return status;
    }

    public TodoEntity(Long id, String content, LocalDate dueDate, TodoStatus status) {
        this.id = id;
        this.content = content;
        this.dueDate = dueDate;
        this.status = status;
    }

    public TodoEntity() {

    }
}
