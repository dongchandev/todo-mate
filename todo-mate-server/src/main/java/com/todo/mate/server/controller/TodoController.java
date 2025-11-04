package com.todo.mate.server.controller;

import com.todo.mate.server.application.CreateTodoUseCase;
import com.todo.mate.server.application.ToggleTodoUseCase;
import com.todo.mate.server.controller.request.CreateTodoRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/todos")
public class TodoController {
    private final CreateTodoUseCase createTodoUseCase;
    private final ToggleTodoUseCase toggleTodoUseCase;

    public TodoController(CreateTodoUseCase createTodoUseCase, ToggleTodoUseCase toggleTodoUseCase) {
        this.createTodoUseCase = createTodoUseCase;
        this.toggleTodoUseCase = toggleTodoUseCase;
    }

    @PostMapping
    public Long createTodo(@RequestBody CreateTodoRequest request) {
        return createTodoUseCase.handle(request.toCommand());
    }

    @PatchMapping("/{id}/toggle")
    public void toggleTodo(@PathVariable Long id) {
        toggleTodoUseCase.handle(id);
    }
}
