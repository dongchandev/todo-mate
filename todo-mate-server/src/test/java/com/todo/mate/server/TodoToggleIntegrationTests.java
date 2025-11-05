package com.todo.mate.server;

import com.todo.mate.server.application.CreateTodoUseCase;
import com.todo.mate.server.application.ToggleTodoUseCase;
import com.todo.mate.server.domain.entity.Todo;
import com.todo.mate.server.enumeration.TodoStatus;
import com.todo.mate.server.infra.db.TodoRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class TodoToggleIntegrationTests {

    @Autowired
    ToggleTodoUseCase toggleTodoUseCase;

    @Autowired
    CreateTodoUseCase createTodoUseCase;

    @Autowired
    TodoRepository repo;

    @Test
    void 토글_하면_DB_상태가_반영된다() {
        // given
        Todo todo = Todo.create("책 읽기", LocalDate.now().plusDays(1), "100p까지 읽기");
        todo = repo.save(todo);

        // when: 유스케이스 호출
        toggleTodoUseCase.handle(todo.getId());

        // then: DB에 반영됐는지 확인
        var found = repo.findById(todo.getId()).orElseThrow();
        assertEquals(TodoStatus.DONE, found.getStatus());
    }

    @Test
    void 존재하지_않는_ID면_예외를_던진다() {
        assertThrows(NoSuchElementException.class, () ->
                toggleTodoUseCase.handle(999L)
        );
    }

    @Test
    void 완료된_Todo를_다시_토글하면_IN_PROGRESS로_변경된다() {
        Todo todo = Todo.create("산책하기", LocalDate.now().plusDays(1), "8시에 산책하기");
        var saved = repo.save(todo);

        toggleTodoUseCase.handle(saved.getId()); // → DONE
        toggleTodoUseCase.handle(saved.getId()); // → 다시 IN_PROGRESS

        var found = repo.findById(saved.getId()).orElseThrow();
        assertEquals(TodoStatus.IN_PROGRESS, found.getStatus());
    }
}
