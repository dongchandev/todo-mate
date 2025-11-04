package com.todo.mate.server.domain;

import com.todo.mate.server.domain.todo.Content;
import com.todo.mate.server.domain.todo.DueDate;
import com.todo.mate.server.domain.exception.InvalidContent;
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
        assertThrows(InvalidContent.class, () -> new Content(input));
    }

    @ParameterizedTest
    @NullSource
    void due_date가_null일_경우_에러를_반환해야_함(LocalDate input) {
        assertThrows(InvalidContent.class, () -> new DueDate(input));
    }

}
