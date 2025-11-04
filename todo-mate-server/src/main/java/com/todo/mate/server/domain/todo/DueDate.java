package com.todo.mate.server.domain.todo;

import com.todo.mate.server.domain.exception.InvalidContent;

import java.time.LocalDate;

public final class DueDate {
    private final LocalDate value;

    public DueDate(LocalDate value) {
        if (value == null)
            throw new InvalidContent("날짜는 null이 될 수 없습니다.");
        if (value.isBefore(LocalDate.now()))
            throw new InvalidContent("등록 날짜는 과거가 될 수 없습니다.");

        this.value = value;
    }

    public static DueDate of (LocalDate date) {
        return new DueDate(date);
    }

    public LocalDate value() { return value; }
}
