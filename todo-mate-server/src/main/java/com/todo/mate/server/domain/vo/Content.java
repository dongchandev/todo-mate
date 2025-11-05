package com.todo.mate.server.domain.vo;

import com.todo.mate.server.domain.exception.InvalidContent;
import com.todo.mate.server.domain.exception.TodoExceptionCode;

public final class Content {
    private String content;

    protected Content() {}

    private Content(String value) {
        if (value == null || value.trim().isEmpty())
            throw new InvalidContent(TodoExceptionCode.CONTENT_NOT_BLANK);
        this.content = value;
    }

    public static Content of (String content) {
        return new Content(content);
    }

    public String value() { return content; }
}
