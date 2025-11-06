package com.todo.mate.server.application;

public record GetMonthDoneCountCommand(
        Integer year,
        Integer month
) {

    public GetMonthDoneCountCommand {
        if (year == null) throw new IllegalArgumentException("year는 필수입니다.");
        if (month == null) throw new IllegalArgumentException("month는 필수입니다.");
    }

    public static GetMonthDoneCountCommand of(Integer year, Integer month) {
        return new GetMonthDoneCountCommand(year, month);
    }
}