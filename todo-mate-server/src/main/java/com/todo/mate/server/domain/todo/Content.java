package com.todo.mate.server.domain.todo;

import com.todo.mate.server.domain.exception.InvalidContent;

public final class Content {
    private String content;

    protected Content() {}

    public Content(String value) {
        if (value == null || value.trim().isEmpty())
            throw new InvalidContent("내용은 비어 있을 수 없습니다.");
        this.content = value;
    }

    public static Content of (String content) {
        return new Content(content);
    }

    public String value() { return content; }
}
