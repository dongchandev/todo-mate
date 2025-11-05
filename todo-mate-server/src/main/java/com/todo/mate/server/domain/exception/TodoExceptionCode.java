package com.todo.mate.server.domain.exception;

public enum TodoExceptionCode implements ExceptionCode {

    CONTENT_NOT_BLANK(400, "내용은 비어있을 수 없습니다."),
    DUE_DATE_NOT_NULL(400, "날짜는 null일 수 없습니다."),
    DUE_DATE_NOT_PAST(400, "등록날짜는 과거가 될 수 없습니다."),
    MEMO_NOT_NULL(400, "메모는 null일 수 없습니다."),
    MEMO_NOT_OVER_1000(400, "메모는 1000자 초과일 수 없습니다."),;


    private final int status;
    private final String message;

    TodoExceptionCode(int status, String message) {
        this.status = status;
        this.message = message;
    }

    @Override
    public int getStatus() {
        return this.status;
    }

    @Override
    public String getExceptionName() {
        return this.name();
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
