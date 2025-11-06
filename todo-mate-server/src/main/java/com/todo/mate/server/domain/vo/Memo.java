package com.todo.mate.server.domain.vo;

import com.todo.mate.server.domain.exception.InvalidValue;
import com.todo.mate.server.domain.exception.TodoExceptionCode;

public class Memo {
    private String memo;

    protected Memo() {}

    private Memo(String memo) {
        if (memo == null)
            throw new InvalidValue(TodoExceptionCode.MEMO_NOT_NULL);

        if (memo.length() > 1000)
            throw new InvalidValue(TodoExceptionCode.MEMO_NOT_OVER_1000);

        this.memo = memo;
    }

    public String value() { return memo; }

    public static Memo of(String value) {
        return new Memo(value);
    }
}
