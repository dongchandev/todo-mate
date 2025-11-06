package com.todo.mate.server.controller.response;

public record GetMonthDoneCountResponse(Long count) {

    public static GetMonthDoneCountResponse of(Long count) {
        return new GetMonthDoneCountResponse(count);
    }
}