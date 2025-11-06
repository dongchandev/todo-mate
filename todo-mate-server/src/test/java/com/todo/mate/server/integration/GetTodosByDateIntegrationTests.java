package com.todo.mate.server.integration;

import com.todo.mate.server.application.GetTodosByDateUseCase;
import com.todo.mate.server.domain.entity.Todo;
import com.todo.mate.server.infra.db.TodoRepository;
import com.todo.mate.server.controller.response.TodoResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class GetTodosByDateIntegrationTests {

    @Autowired
    private GetTodosByDateUseCase getTodosByDateUseCase;

    @Autowired
    private TodoRepository todoRepository;

    @Test
    void 특정_날짜의_Todo를_조회한다() {
        todoRepository.deleteAll();
        LocalDate targetDate = LocalDate.now();

        todoRepository.save(Todo.create("스터디 준비", targetDate));
        todoRepository.save(Todo.create("테스트 코드 작성", targetDate));
        todoRepository.save(Todo.create("운동하기", targetDate.plusDays(1)));

        List<TodoResponse> result = getTodosByDateUseCase.handle(targetDate);

        assertThat(result)
                .hasSize(2)
                .extracting(TodoResponse::text)
                .containsExactlyInAnyOrder("스터디 준비", "테스트 코드 작성");
    }
}
