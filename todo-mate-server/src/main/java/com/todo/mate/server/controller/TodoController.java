package com.todo.mate.server.controller;

import com.todo.mate.server.application.CreateTodoUseCase;
import com.todo.mate.server.application.DeleteTodoUseCase;
import com.todo.mate.server.application.ToggleTodoUseCase;
import com.todo.mate.server.controller.request.CreateTodoRequest;
import com.todo.mate.server.controller.response.IDResponse;
import com.todo.mate.server.controller.response.Response;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/todos")
public class TodoController {
    private final CreateTodoUseCase createTodoUseCase;
    private final ToggleTodoUseCase toggleTodoUseCase;
    private final DeleteTodoUseCase deleteTodoUseCase;

    public TodoController(CreateTodoUseCase createTodoUseCase, ToggleTodoUseCase toggleTodoUseCase, DeleteTodoUseCase deleteTodoUseCase) {
        this.createTodoUseCase = createTodoUseCase;
        this.toggleTodoUseCase = toggleTodoUseCase;
        this.deleteTodoUseCase = deleteTodoUseCase;
    }

    @PostMapping
    public Response<IDResponse> createTodo(@RequestBody CreateTodoRequest request) {
        return Response.created(
                "Todo 생성 성공",
                createTodoUseCase.handle(request.toCommand())
        );
    }

    @PatchMapping("/{id}/toggle")
    public Response<Void> toggleTodo(@PathVariable Long id) {
        toggleTodoUseCase.handle(id);
        return Response.ok("Todo 체크 성공");
    }

    @DeleteMapping("/{id}")
    public Response<Void> deleteTodo(@PathVariable Long id) {
        deleteTodoUseCase.handle(id);
        return Response.ok("Todo 삭제 성공");
    }
}
