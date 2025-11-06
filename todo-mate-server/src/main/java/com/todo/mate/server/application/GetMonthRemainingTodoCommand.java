package com.todo.mate.server.application;

public record GetMonthRemainingTodoCommand(
        Integer year,
        Integer month
) {

    public GetMonthRemainingTodoCommand {
        if (year == null) throw new IllegalArgumentException("year는 필수입니다.");
        if (month == null) throw new IllegalArgumentException("month는 필수입니다.");
    }

    public static GetMonthRemainingTodoCommand of(Integer year, Integer month) {
        return new GetMonthRemainingTodoCommand(year, month);
    }
}
