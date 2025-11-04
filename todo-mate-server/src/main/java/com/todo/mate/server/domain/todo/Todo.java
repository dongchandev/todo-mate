package com.todo.mate.server.domain.todo;

import com.todo.mate.server.domain.exception.InvalidContent;
import com.todo.mate.server.enumeration.TodoStatus;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "tb_todo")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "content"))
    })
    private Content content;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "due_date"))
    })
    private DueDate dueDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TodoStatus status;

    protected Todo() {}

    public static Todo create(String content, LocalDate dueDate) {
        var due = DueDate.of(dueDate);
        due.validateIsPast();

        Todo todo = new Todo();
        todo.content = Content.of(content);
        todo.dueDate = due;
        todo.status = TodoStatus.IN_PROGRESS;
        return todo;
    }

    // ✅ 상태 토글 메서드
    public void toggleStatus() {
        this.status = (this.status == TodoStatus.DONE)
                ? TodoStatus.IN_PROGRESS
                : TodoStatus.DONE;
    }

    // ✅ getter
    public Long getId() { return id; }
    public String getContent() { return content.value(); }
    public LocalDate getDueDate() { return dueDate.value(); }
    public TodoStatus getStatus() { return status; }
}
