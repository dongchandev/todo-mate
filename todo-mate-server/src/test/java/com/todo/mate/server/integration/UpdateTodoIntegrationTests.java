package com.todo.mate.server.integration;

import com.todo.mate.server.application.UpdateTodoCommand;
import com.todo.mate.server.application.UpdateTodoUseCase;
import com.todo.mate.server.domain.entity.Todo;
import com.todo.mate.server.domain.exception.InvalidValue;
import com.todo.mate.server.infra.db.TodoRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.then;

@SpringBootTest
@Transactional
class UpdateTodoIntegrationTests {

    @Autowired private TodoRepository todoRepository;
    @Autowired private UpdateTodoUseCase updateTodoUseCase;

    @Test
    void content만_수정할_수_있다() {
        Todo todo = todoRepository.save(Todo.create("공부하기", LocalDate.now()));
        updateTodoUseCase.handle(UpdateTodoCommand.of(todo.getId(), "운동하기", null));

        assertThat(todo.getContent()).isEqualTo("운동하기");
        assertThat(todo.getMemo()).isEqualTo("");
    }

    @Test
    void memo만_수정할_수_있다() {
        Todo todo = todoRepository.save(Todo.create("공부하기", LocalDate.now()));

        updateTodoUseCase.handle(UpdateTodoCommand.of(todo.getId(), null, "메모 내용"));
        assertThat(todo.getMemo()).isEqualTo("메모 내용");
        assertThat(todo.getContent()).isEqualTo("공부하기");
    }

    @Test
    void 존재하지_않는_id일_경우_예외_발생() {
        assertThatThrownBy(() -> updateTodoUseCase.handle(UpdateTodoCommand.of(99L, "수정", null)))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void content와_memo_모두_null이면_예외_발생() {
        Todo todo = todoRepository.save(Todo.create("공부하기", LocalDate.now()));

        assertThatThrownBy(() -> updateTodoUseCase.handle(UpdateTodoCommand.of(todo.getId(), null, null)))
                .isInstanceOf(InvalidValue.class);
    }
}
