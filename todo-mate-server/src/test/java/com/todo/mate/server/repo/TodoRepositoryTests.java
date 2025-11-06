package com.todo.mate.server.repo;

import com.todo.mate.server.domain.entity.Todo;
import com.todo.mate.server.domain.vo.DueDate;
import com.todo.mate.server.infra.db.TodoDateCountProjection;
import com.todo.mate.server.infra.db.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TodoRepositoryTests {

    @Autowired
    private TodoRepository todoRepository;

    @BeforeEach
    void clean() {
        todoRepository.deleteAll();
    }

    @Test
    void 날짜별_남은_Todo_수를_DB에서_집계한다() {
        todoRepository.save(Todo.create("A", LocalDate.of(2025, 11, 7)));
        Todo doneTodo = todoRepository.save(Todo.create("B", LocalDate.of(2025, 11, 6)));
        todoRepository.save(Todo.create("C", LocalDate.of(2025, 11, 8)));

        doneTodo.toggleStatus();
        todoRepository.save(doneTodo);

        List<TodoDateCountProjection> result = todoRepository.countRemainingByDueDate(2025, 11);
        System.out.println(result.stream().map(Record::toString).toList());
        assertThat(result).hasSize(2);

        assertThat(result).anySatisfy(proj -> {
            if (proj.date().equals(LocalDate.of(2025, 11, 1))) {
                assertThat(proj.count()).isEqualTo(1L);
            }
        });
    }

    @Test
    void 특정_연월의_완료된_Todo_개수를_DB에서_집계한다() {
        LocalDate today = LocalDate.now();
        todoRepository.save(Todo.create("A", today));
        todoRepository.save(Todo.create("B", today.plusDays(1)));
        Todo done = todoRepository.save(Todo.create("C", today.plusDays(1)));
        done.toggleStatus();
        todoRepository.save(done);

        long doneCount = todoRepository.countDoneByMonth(2025, 11);

        assertThat(doneCount).isEqualTo(1L);
    }

    @Test
    void 특정_날짜의_Todo를_DB에서_조회한다() {
        LocalDate today = LocalDate.now();

        todoRepository.save(Todo.create("A", today));
        todoRepository.save(Todo.create("B", today));
        todoRepository.save(Todo.create("C", today.plusDays(1)));

        List<Todo> result = todoRepository.findAllByDueDate(DueDate.of(today));

        assertThat(result)
                .hasSize(2)
                .extracting("content")
                .containsExactlyInAnyOrder("A", "B");
    }
}
