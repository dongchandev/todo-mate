package com.todo.mate.server.controller.response;

import com.todo.mate.server.infra.db.TodoDateCountProjection;

import java.time.LocalDate;
import java.util.List;

public record TodoDateCountResponse(
        LocalDate date,
        Long count
) {
    public static List<TodoDateCountResponse> of(List<TodoDateCountProjection> projections) {
        return projections.stream()
                .map(proj ->
                        new TodoDateCountResponse(proj.date(), proj.count())
                ).toList();
    }
}
