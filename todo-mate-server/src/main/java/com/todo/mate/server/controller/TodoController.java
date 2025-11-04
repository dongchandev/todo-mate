package com.todo.mate.server.controller;

import com.todo.mate.server.application.CreateTodoUseCase;
import com.todo.mate.server.controller.request.CreateTodoRequest;
import com.todo.mate.server.domain.todo.TodoId;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/todos")
public class TodoController {
    private final CreateTodoUseCase createTodoUseCase;

    public TodoController(CreateTodoUseCase createTodoUseCase) {
        this.createTodoUseCase = createTodoUseCase;
    }

    @PostMapping
    public TodoId createTodo(@RequestBody CreateTodoRequest request) {
        return createTodoUseCase.handle(request.toCommand());
    }
}
