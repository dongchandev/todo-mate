package com.todo.mate.server.integration;

import com.todo.mate.server.application.CreateTodoCommand;
import com.todo.mate.server.application.CreateTodoUseCase;
import com.todo.mate.server.controller.response.TodoResponse;
import com.todo.mate.server.domain.entity.Todo;
import com.todo.mate.server.enumeration.TodoStatus;
import com.todo.mate.server.infra.db.TodoRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class TodoAddIntegrationTests {
    @Autowired
    private CreateTodoUseCase createTodoUseCase;

    @Autowired
    private TodoRepository todoRepository;

    @Test
    void 정상_생성시_DB에_저장되고_ID_반환() {
        TodoResponse res = createTodoUseCase.handle(CreateTodoCommand.of("독서하기", LocalDate.now()));

        Todo entity = todoRepository.findById(res.id()).orElseThrow();
        assertEquals("독서하기", entity.getContent());
        assertEquals(TodoStatus.IN_PROGRESS, entity.getStatus());
    }
}
