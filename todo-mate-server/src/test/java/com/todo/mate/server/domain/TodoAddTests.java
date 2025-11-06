package com.todo.mate.server.domain;

import com.todo.mate.server.domain.exception.InvalidValue;
import com.todo.mate.server.domain.entity.Todo;
import com.todo.mate.server.enumeration.TodoStatus;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
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
                InvalidValue.class,
                () -> Todo.create("공부하기", LocalDate.now().minusDays(1))
        );
    }

    @Test
    void Todo만들기의_결과가_null_이_아니여야_함() {
        Todo todo = Todo.create("공부하기", LocalDate.now());
        assertThat(todo).as("todo not created").isNotNull();
    }

    @Test
    void 생성시_상태는_IN_PROGRESS_여야_함() {
        Todo todo = Todo.create("공부하기",LocalDate.now());
        assertEquals(TodoStatus.IN_PROGRESS, todo.getStatus());
    }

}
