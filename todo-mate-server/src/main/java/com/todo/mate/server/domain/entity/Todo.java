package com.todo.mate.server.domain.entity;

import com.todo.mate.server.domain.exception.InvalidValue;
import com.todo.mate.server.domain.exception.TodoExceptionCode;
import com.todo.mate.server.domain.vo.Content;
import com.todo.mate.server.domain.vo.DueDate;
import com.todo.mate.server.domain.vo.Memo;
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

    @Column(nullable = false)
    private Boolean isDeleted = Boolean.FALSE;

    protected Todo() {}

    public static Todo create(String content, LocalDate dueDate) {
        var due = DueDate.of(dueDate);
        due.validateIsPast();

        Todo todo = new Todo();
        todo.content = Content.of(content);
        todo.dueDate = due;
        todo.status = TodoStatus.IN_PROGRESS;
        todo.memo = Memo.of("");
        todo.isDeleted = Boolean.FALSE;
        return todo;
    }

    public void update(String newContent, String newMemo) {
        if (newContent == null && newMemo == null) {
            throw new InvalidValue(TodoExceptionCode.UPDATE_VALUE_REQUIRED);
        }

        if (newContent != null) {
            this.content = Content.of(newContent);
        }

        if (newMemo != null) {
            this.memo = Memo.of(newMemo);
        }
    }

    public void delete() {
        if (isDeleted) throw new InvalidValue(TodoExceptionCode.DELETED_TODO_NOT_DELETE);
        this.isDeleted = Boolean.TRUE;
    }

    public void toggleStatus() {
        this.status = (this.status == TodoStatus.DONE)
                ? TodoStatus.IN_PROGRESS
                : TodoStatus.DONE;
    }

    public Long getId() { return id; }
    public String getContent() { return content.value(); }
    public TodoStatus getStatus() { return status; }
    public String getMemo() { return memo.value(); }
    public Boolean getIsDeleted() { return isDeleted; }
}
