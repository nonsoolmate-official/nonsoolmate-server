package com.nonsoolmate.nonsoolmateServer.domain.examRecord.exception;

import com.nonsoolmate.nonsoolmateServer.global.error.exception.ExceptionType;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum UniversityExamRecordExceptionType implements ExceptionType {
    NOT_FOUND_UNIVERSITY_EXAM_RECORD(HttpStatus.NOT_FOUND, "존재하지 않는 시험 응시 기록입니다."),
    CREATE_UNIVERSITY_EXAM_RECORD_FAIL(HttpStatus.BAD_REQUEST, "대학 시험 기록 생성에 실패했습니다."),
    INVALID_UNIVERSITY_EXAM_RECORD_RESULT_FILE_NAME(HttpStatus.BAD_REQUEST, "첨삭이 완료되지 않았습니다."),

    ALREADY_CREATE_EXAM_RECORD(HttpStatus.BAD_REQUEST, "이미 응시한 대학 시험입니다");

    private final HttpStatus status;
    private final String message;

    @Override
    public HttpStatus status() {
        return this.status;
    }

    @Override
    public String message() {
        return this.message;
    }
}
