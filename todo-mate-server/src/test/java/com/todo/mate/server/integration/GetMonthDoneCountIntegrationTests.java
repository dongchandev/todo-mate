package com.todo.mate.server.integration;

import com.todo.mate.server.application.GetMonthDoneCountCommand;
import com.todo.mate.server.application.GetMonthDoneCountUseCase;
import com.todo.mate.server.controller.response.GetMonthDoneCountResponse;
import com.todo.mate.server.domain.entity.Todo;
import com.todo.mate.server.infra.db.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class GetMonthDoneCountIntegrationTests {
    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private GetMonthDoneCountUseCase getMonthDoneCountUseCase;

    @BeforeEach
    void clean() {
        todoRepository.deleteAll();
    }

    @Test
    void 특정_연월의_완료된_Todo_개수를_조회한다() {
        LocalDate today = LocalDate.now();
        Todo todo1 = todoRepository.save(Todo.create("A", today));
        Todo todo2 = todoRepository.save(Todo.create("B", today));
        todo1.toggleStatus(); // DONE
        todoRepository.save(todo1);

        GetMonthDoneCountResponse result =
                getMonthDoneCountUseCase.handle(GetMonthDoneCountCommand.of(today.getYear(), today.getMonthValue()));

        assertThat(result.count()).isEqualTo(1L);
    }
}
