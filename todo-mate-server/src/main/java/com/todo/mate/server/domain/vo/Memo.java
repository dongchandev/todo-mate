package com.todo.mate.server.domain.vo;

import com.todo.mate.server.domain.exception.InvalidContent;

public class Memo {
    private String memo;

    protected Memo() {}

    private Memo(String memo) {
        if (memo == null)
            throw new InvalidContent("메모는 null이 될 수 없습니다.");

        if (memo.length() > 1000)
            throw new InvalidContent("메모의 길이는 1000이상일 수 없습니다.");

        this.memo = memo;
    }

    public String value() { return memo; }

    public static Memo of(String value) {
        return new Memo(value);
    }
}
