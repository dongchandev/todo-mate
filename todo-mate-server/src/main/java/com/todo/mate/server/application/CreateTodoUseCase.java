package com.todo.mate.server.application;

import com.todo.mate.server.domain.todo.Todo;
import com.todo.mate.server.domain.todo.TodoId;
import com.todo.mate.server.infra.db.TodoRepository;
import com.todo.mate.server.mapper.TodoMapper;
import org.springframework.stereotype.Component;

@Component
public class CreateTodoUseCase {
    private final TodoRepository todoRepository;

    public CreateTodoUseCase(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public TodoId handle(CreateTodoCommand cmd) {
        Todo created = Todo.create(cmd.content(), cmd.dueDate());
        Todo todo = TodoMapper.toDomain(
                todoRepository.save(TodoMapper.toEntity(created))
        );
        return todo.getId();
    }
}
