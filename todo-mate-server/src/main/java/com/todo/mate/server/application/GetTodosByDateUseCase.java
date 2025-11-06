package com.todo.mate.server.application;

import com.todo.mate.server.controller.response.TodoResponse;
import com.todo.mate.server.domain.vo.DueDate;
import com.todo.mate.server.infra.db.TodoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class GetTodosByDateUseCase {

    private final TodoRepository todoRepository;

    public GetTodosByDateUseCase(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<TodoResponse> handle(LocalDate date) {
        return TodoResponse.of(todoRepository.findAllByDueDate(DueDate.of(date)));
    }
}
