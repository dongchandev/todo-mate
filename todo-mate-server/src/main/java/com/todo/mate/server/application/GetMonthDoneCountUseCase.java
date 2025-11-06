package com.todo.mate.server.application;

import com.todo.mate.server.controller.response.GetMonthDoneCountResponse;
import com.todo.mate.server.infra.db.TodoRepository;
import org.springframework.stereotype.Component;

@Component
public class GetMonthDoneCountUseCase {
    private final TodoRepository todoRepository;

    public GetMonthDoneCountUseCase(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public GetMonthDoneCountResponse handle(GetMonthDoneCountCommand cmd) {
        return GetMonthDoneCountResponse.of(
                todoRepository.countDoneByMonth(cmd.year(), cmd.month())
        );
    }
}
