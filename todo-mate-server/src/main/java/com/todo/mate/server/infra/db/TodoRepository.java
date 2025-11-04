package com.todo.mate.server.infra.db;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<TodoEntity, Long> {
}
