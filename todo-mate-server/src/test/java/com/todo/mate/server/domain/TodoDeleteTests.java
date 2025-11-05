package com.todo.mate.server.domain;

import com.todo.mate.server.domain.entity.Todo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class TodoDeleteTests {
    @Test
    void todo를_삭제하면_isDeleted가_true로_바껴야_함(){
        Todo todo = Todo.create("소프트 삭제 테스트", LocalDate.now().plusDays(1), "isDeleted가 true가 되야 함.");
        todo.delete();
        assertThat(todo.getIsDeleted()).isTrue();
    }

}
