package com.todo.mate.server.application;

import com.todo.mate.server.controller.response.TodoDateCountResponse;
import com.todo.mate.server.infra.db.TodoDateCountProjection;
import com.todo.mate.server.infra.db.TodoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Transactional
public class GetMonthRemainingTodosUseCase {
    private final TodoRepository todoRepository;

    public GetMonthRemainingTodosUseCase(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<TodoDateCountResponse> handle(GetMonthRemainingTodoCommand cmd) {
        List<TodoDateCountProjection> dateCount = todoRepository.countRemainingByDueDate(cmd.year(), cmd.month());
        return TodoDateCountResponse.of(dateCount);
    }
}
