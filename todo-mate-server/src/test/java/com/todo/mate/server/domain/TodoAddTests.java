package com.todo.mate.server.domain;

import com.todo.mate.server.application.CreateTodoCommand;
import com.todo.mate.server.application.CreateTodoUseCase;
import com.todo.mate.server.controller.response.IDResponse;
import com.todo.mate.server.domain.exception.InvalidContent;
import com.todo.mate.server.domain.entity.Todo;
import com.todo.mate.server.enumeration.TodoStatus;
import com.todo.mate.server.infra.db.TodoRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class TodoAddTests {

    @Test
    void Todo만들기의_날짜는_과거로_설정할_수_없음() {
        assertThrows(
                InvalidContent.class,
                () -> Todo.create("공부하기", LocalDate.now().minusDays(1),"100p까지 공부하기")
        );
    }

    @Test
    void Todo만들기의_결과가_null_이_아니여야_함() {
        Todo todo = Todo.create("공부하기", LocalDate.now(),"100p까지 공부하기");
        assertThat(todo).as("todo not created").isNotNull();
    }

    @Test
    void 생성시_상태는_IN_PROGRESS_여야_함() {
        Todo todo = Todo.create("공부하기",LocalDate.now(),"100p까지 공부하기");
        assertEquals(TodoStatus.IN_PROGRESS, todo.getStatus());
    }

}
