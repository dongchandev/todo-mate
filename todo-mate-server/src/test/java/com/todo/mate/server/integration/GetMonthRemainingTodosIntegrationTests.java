package com.todo.mate.server.integration;

import com.todo.mate.server.application.GetMonthRemainingTodoCommand;
import com.todo.mate.server.application.GetMonthRemainingTodosUseCase;
import com.todo.mate.server.controller.response.TodoDateCountResponse;
import com.todo.mate.server.domain.entity.Todo;
import com.todo.mate.server.infra.db.TodoRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
@Transactional
class GetMonthRemainingTodosIntegrationTests {

    @Autowired private TodoRepository todoRepository;
    @Autowired private GetMonthRemainingTodosUseCase getMonthRemainingTodosUseCase;

    @Test
    void 특정_연월의_날짜별_남은_Todo_수를_조회한다() {
        LocalDate today = LocalDate.now();
        todoRepository.save(Todo.create("A", today));
        todoRepository.save(Todo.create("B", today));
        Todo done = todoRepository.save(Todo.create("C", today));
        done.toggleStatus();
        todoRepository.save(done);

        List<TodoDateCountResponse> result = getMonthRemainingTodosUseCase.handle(GetMonthRemainingTodoCommand.of(today.getYear(), today.getMonth().getValue()));

        assertThat(result)
                .anySatisfy(res -> {
                    assertThat(res.date()).isEqualTo(today.toString());
                    assertThat(res.count()).isEqualTo(2L);
                })
                .hasSize(1);
    }

    @Test
    void 인자에_null이_들어올_때_에러를_반환한다() {
        assertThatThrownBy(() -> GetMonthRemainingTodoCommand.of(null, 1))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> GetMonthRemainingTodoCommand.of(2025, null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
