package com.todo.mate.server.infra.db;

import com.todo.mate.server.domain.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.NoSuchElementException;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    default Todo findByIdOrThrow(long id) {
        return findById(id).orElseThrow(NoSuchElementException::new);
    }
}
