package com.todo.mate.server.domain;

import com.todo.mate.server.domain.entity.Todo;
import com.todo.mate.server.domain.exception.InvalidValue;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class TodoEditTests {

    @Test
    void content만_수정할_수_있다() {
        Todo todo = Todo.create("공부하기", LocalDate.now());

        todo.update("운동하기", null);

        assertThat(todo.getContent()).isEqualTo("운동하기");
        assertThat(todo.getMemo()).isEqualTo("");
    }

    @Test
    void memo만_수정할_수_있다() {
        Todo todo = Todo.create("공부하기", LocalDate.now());
        todo.update(null, "메모 내용");

        assertThat(todo.getMemo()).isEqualTo("메모 내용");
        assertThat(todo.getContent()).isEqualTo("공부하기");
    }

    @Test
    void content와_memo_모두_null이면_예외_발생() {
        Todo todo = Todo.create("공부하기", LocalDate.now());

        assertThatThrownBy(() -> todo.update(null, null))
                .isInstanceOf(InvalidValue.class);
    }

    @Test
    void memo가_1000자를_초과하면_예외_발생() {
        Todo todo = Todo.create("공부하기", LocalDate.now());
        String longMemo = "a".repeat(1001);

        assertThatThrownBy(() -> todo.update(null, longMemo))
                .isInstanceOf(InvalidValue.class);
    }
}
