package com.todo.mate.server.domain.entity;

import com.todo.mate.server.domain.vo.Content;
import com.todo.mate.server.domain.vo.DueDate;
import com.todo.mate.server.domain.vo.Memo;
import com.todo.mate.server.enumeration.TodoStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Entity
@Table(name = "tb_todo")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "content", column = @Column(name = "content", nullable = false))
    })
    private Content content;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "due_date", column = @Column(name = "due_date", nullable = false))
    })
    private DueDate dueDate;

    @Embedded
    @AttributeOverride(name = "memo", column = @Column(name = "memo", columnDefinition = "TEXT", nullable = false))
    private Memo memo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TodoStatus status;

    protected Todo() {}

    public static Todo create(String content, LocalDate dueDate, String memo) {
        var due = DueDate.of(dueDate);
        due.validateIsPast();

        Todo todo = new Todo();
        todo.content = Content.of(content);
        todo.dueDate = due;
        todo.status = TodoStatus.IN_PROGRESS;
        todo.memo = Memo.of(memo);
        return todo;
    }

    public void toggleStatus() {
        this.status = (this.status == TodoStatus.DONE)
                ? TodoStatus.IN_PROGRESS
                : TodoStatus.DONE;
    }

    public Long getId() { return id; }
    public String getContent() { return content.value(); }
    public LocalDate getDueDate() { return dueDate.value(); }
    public TodoStatus getStatus() { return status; }
    public String getMemo() { return memo.value(); }
}
