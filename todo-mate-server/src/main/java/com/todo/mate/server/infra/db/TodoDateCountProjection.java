package com.todo.mate.server.infra.db;

import java.time.LocalDate;

public record TodoDateCountProjection(
        LocalDate date,
        Long count
) {
}
