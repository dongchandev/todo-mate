package com.todo.mate.server.controller;

import com.todo.mate.server.application.*;
import com.todo.mate.server.controller.request.CreateTodoRequest;
import com.todo.mate.server.controller.request.UpdateTodoRequest;
import com.todo.mate.server.controller.response.Response;
import com.todo.mate.server.controller.response.TodoDateCountResponse;
import com.todo.mate.server.controller.response.TodoResponse;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {
    private final CreateTodoUseCase createTodoUseCase;
    private final ToggleTodoUseCase toggleTodoUseCase;
    private final DeleteTodoUseCase deleteTodoUseCase;
    private final UpdateTodoUseCase updateTodoUseCase;
    private final GetMonthRemainingTodosUseCase  getMonthRemainingTodosUseCase;

    public TodoController(CreateTodoUseCase createTodoUseCase, ToggleTodoUseCase toggleTodoUseCase, DeleteTodoUseCase deleteTodoUseCase, UpdateTodoUseCase updateTodoUseCase, GetMonthRemainingTodosUseCase getMonthRemainingTodosUseCase) {
        this.createTodoUseCase = createTodoUseCase;
        this.toggleTodoUseCase = toggleTodoUseCase;
        this.deleteTodoUseCase = deleteTodoUseCase;
        this.updateTodoUseCase = updateTodoUseCase;
        this.getMonthRemainingTodosUseCase = getMonthRemainingTodosUseCase;
    }

    @PostMapping
    public Response<TodoResponse> createTodo(@RequestBody CreateTodoRequest request) {
        return Response.created(
                "Todo 생성 성공",
                createTodoUseCase.handle(request.toCommand())
        );
    }

    @PatchMapping("/{id}")
    public Response<TodoResponse> updateTodo(@PathVariable Long id, @RequestBody UpdateTodoRequest request) {

        return Response.ok(
                "Todo 수정 성공",
                updateTodoUseCase.handle(request.toCommand(id))
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

    @GetMapping("/remaining")
    public Response<List<TodoDateCountResponse>> getTodoDateCount(
            @RequestParam Integer year,
            @RequestParam Integer month
    ) {
        return Response.ok(
                "날짜마다 남은 Todo 갯수 반환 성공",
                getMonthRemainingTodosUseCase.handle(GetMonthRemainingTodoCommand.of(year, month))
        );
    }

    @GetMapping("/done-count")
    public Response<List<TodoDateCountResponse>> getTodoDoneCount(
            @RequestParam Integer year,
            @RequestParam Integer month
    ) {
        return Response.ok(
                "해당 달에 달성한 Todo 갯수 반환 성공",
                getMonthRemainingTodosUseCase.handle(GetMonthRemainingTodoCommand.of(year, month))
        );
    }
}
