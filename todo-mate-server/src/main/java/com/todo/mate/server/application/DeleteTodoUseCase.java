package com.todo.mate.server.application;

import com.todo.mate.server.domain.entity.Todo;
import com.todo.mate.server.infra.db.TodoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class DeleteTodoUseCase {
    private final TodoRepository todoRepository;

    public DeleteTodoUseCase(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public void handle(long id) {
        Todo todo = todoRepository.findByIdOrThrow(id);
        todo.delete();
        todoRepository.save(todo);
    }
}
