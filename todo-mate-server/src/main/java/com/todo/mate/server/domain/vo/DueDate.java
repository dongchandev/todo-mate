package com.todo.mate.server.domain.vo;

import com.todo.mate.server.domain.exception.InvalidValue;
import com.todo.mate.server.domain.exception.TodoExceptionCode;

import java.time.LocalDate;

public final class DueDate {
    private LocalDate dueDate;

    protected DueDate() {}

    private DueDate(LocalDate value) {
        if (value == null)
            throw new InvalidValue(TodoExceptionCode.DUE_DATE_NOT_NULL);

        this.dueDate = value;
    }

    public static DueDate of (LocalDate date) {
        return new DueDate(date);
    }

    public void validateIsPast () {
        if (dueDate.isBefore(LocalDate.now()))
            throw new InvalidValue(TodoExceptionCode.DUE_DATE_NOT_PAST);
    }

    public LocalDate value() { return dueDate; }
}
