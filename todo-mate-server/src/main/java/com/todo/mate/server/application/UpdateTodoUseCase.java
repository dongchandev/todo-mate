package com.todo.mate.server.application;

import com.todo.mate.server.domain.entity.Todo;
import com.todo.mate.server.infra.db.TodoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class UpdateTodoUseCase {
    private final TodoRepository todoRepository;
    public UpdateTodoUseCase(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public TodoResponse handle(UpdateTodoCommand cmd) {
        Todo todo = todoRepository.findByIdOrThrow(cmd.id());
        todo.update(cmd.content(), cmd.memo());
        return TodoResponse.of(todoRepository.save(todo));
    }
}
