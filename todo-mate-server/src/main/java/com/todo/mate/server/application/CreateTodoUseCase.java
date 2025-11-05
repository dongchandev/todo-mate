package com.todo.mate.server.application;

import com.todo.mate.server.domain.entity.Todo;
import com.todo.mate.server.infra.db.TodoRepository;
import org.springframework.stereotype.Component;

@Component
public class CreateTodoUseCase {
    private final TodoRepository todoRepository;

    public CreateTodoUseCase(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public Long handle(CreateTodoCommand cmd) {
        Todo created = Todo.create(cmd.content(), cmd.dueDate(), cmd.memo());
        Todo todo = todoRepository.save(created);
        return todo.getId();
    }
}
