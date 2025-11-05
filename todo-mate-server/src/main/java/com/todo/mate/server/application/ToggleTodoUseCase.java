package com.todo.mate.server.application;

import com.todo.mate.server.domain.entity.Todo;
import com.todo.mate.server.infra.db.TodoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
@Transactional(rollbackOn = RuntimeException.class)
public class ToggleTodoUseCase {
    private final TodoRepository todoRepository;
    public ToggleTodoUseCase(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public void handle(Long id){
        Todo todo = todoRepository.findByIdOrThrow(id);
        todo.toggleStatus();
//        todoRepository.save(TodoMapper.toEntityWithId(todo));
    }
}
