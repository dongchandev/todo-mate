package com.todo.mate.server.domain;

import com.todo.mate.server.domain.entity.Todo;
import com.todo.mate.server.enumeration.TodoStatus;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static com.todo.mate.server.domain.entity.Todo.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class TodoToggleStatusTests {


    @Test
    void 생성시_IN_PROGRESS이며_toggle하면_DONE이_된다() {
        Todo todo = create("공부하기", LocalDate.now().plusDays(1),"100p까지 공부하기");

        assertEquals(TodoStatus.IN_PROGRESS, todo.getStatus());

        todo.toggleStatus();

        assertEquals(TodoStatus.DONE, todo.getStatus());
    }

    @Test
    void 두번_toggle하면_원래_상태로_돌아와야_함() {
        Todo todo = create("공부하기", LocalDate.now().plusDays(1),"100p까지 공부하기");

        todo.toggleStatus();
        todo.toggleStatus();

        assertEquals(TodoStatus.IN_PROGRESS, todo.getStatus());
    }
}
