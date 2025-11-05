package com.todo.mate.server.integration;

import com.todo.mate.server.application.CreateTodoCommand;
import com.todo.mate.server.application.CreateTodoUseCase;
import com.todo.mate.server.application.DeleteTodoUseCase;
import com.todo.mate.server.controller.response.IDResponse;
import com.todo.mate.server.domain.entity.Todo;
import com.todo.mate.server.infra.db.TodoRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
@Transactional
class TodoDeleteIntegrationTests {

    @Autowired TodoRepository todoRepository;
    @Autowired CreateTodoUseCase createTodoUseCase;
    @Autowired DeleteTodoUseCase deleteTodoUseCase;

    @Test
    void 삭제하면_isDeleted가_true로_변경되어야_함() {
        Long todoId = createTodoUseCase.handle(CreateTodoCommand.of("삭제 테스트", LocalDate.now().plusDays(1))).id();
        deleteTodoUseCase.handle(todoId);
        Todo todo = todoRepository.findByIdOrThrow(todoId);
        assertThat(todo.getIsDeleted()).isTrue();
    }

    @Test
    void 투두를_삭제하면_DB에는_남고_조회에서는_빠짐() {
        Long todoId = createTodoUseCase.handle(CreateTodoCommand.of("삭제 테스트", LocalDate.now().plusDays(1))).id();

        deleteTodoUseCase.handle(todoId);

        var byId = todoRepository.findById(todoId);
        var byActive = todoRepository.findByIdAndIsDeletedFalse(todoId);

        assertThat(byId).isPresent();
        assertThat(byActive).isEmpty();
    }
}