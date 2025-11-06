package com.todo.mate.server.infra.db;

import com.todo.mate.server.domain.entity.Todo;
import com.todo.mate.server.domain.vo.DueDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    default Todo findByIdOrThrow(long id) {
        return findById(id).orElseThrow(()->
                new NoSuchElementException("해당 id의 todo를 찾을 수 없습니다.")
        );
    }

    @Query("""
        SELECT new com.todo.mate.server.infra.db.TodoDateCountProjection(t.dueDate.dueDate, COUNT(t))
        FROM Todo t
        WHERE YEAR(t.dueDate) = :year
          AND MONTH(t.dueDate) = :month
          AND t.status <> 'DONE'
        GROUP BY t.dueDate
        ORDER BY t.dueDate
    """)
    List<TodoDateCountProjection> countRemainingByDueDate(
            @Param("year") int year,
            @Param("month") int month
    );

    List<Todo> findAllByDueDate(DueDate dueDate);

    @Query("""
        SELECT COUNT(t)
        FROM Todo t
        WHERE YEAR(t.dueDate) = :year
          AND MONTH(t.dueDate) = :month
          AND t.status = 'DONE'
    """)
    long countDoneByMonth(@Param("year") int year, @Param("month") int month);
}
