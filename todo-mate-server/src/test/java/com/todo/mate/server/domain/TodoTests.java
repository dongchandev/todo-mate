package com.todo.mate.server.domain;

import com.todo.mate.server.domain.vo.Content;
import com.todo.mate.server.domain.vo.DueDate;
import com.todo.mate.server.domain.exception.InvalidContent;
import com.todo.mate.server.domain.vo.Memo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TodoTests {
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = { " ", "   " })
    void content가_null이거나_빈_문자열일_경우_에러를_반환해야_함(String input) {
        assertThrows(InvalidContent.class, () -> Content.of(input));
    }

    @ParameterizedTest
    @NullSource
    void due_date가_null일_경우_에러를_반환해야_함(LocalDate input) {
        assertThrows(InvalidContent.class, () -> DueDate.of(input));
    }

    @ParameterizedTest
    @NullSource
    @MethodSource("invalidMemos")
    void memo가_1000자_이상이거나_Null일_경우_에러를_반환해야_함(String input) {
        assertThrows(InvalidContent.class, () -> Memo.of(input));
    }

    static Stream<String> invalidMemos() {
        return Stream.of(
                "a".repeat(1001)
        );
    }
}
