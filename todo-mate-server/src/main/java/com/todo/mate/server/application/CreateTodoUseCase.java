package com.todo.mate.server.application;

import com.todo.mate.server.controller.response.IDResponse;
import com.todo.mate.server.domain.entity.Todo;
import com.todo.mate.server.infra.db.TodoRepository;
import org.springframework.stereotype.Component;

@Component
public class CreateTodoUseCase {
    private final TodoRepository todoRepository;

    public CreateTodoUseCase(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public IDResponse handle(CreateTodoCommand cmd) {
        Todo created = Todo.create(cmd.content(), cmd.dueDate());
        Todo todo = todoRepository.save(created);
        return IDResponse.of(todo.getId());
    }
}
