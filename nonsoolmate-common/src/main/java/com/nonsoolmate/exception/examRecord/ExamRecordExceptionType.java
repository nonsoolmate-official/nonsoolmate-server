package com.nonsoolmate.exception.examRecord;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;

import com.nonsoolmate.exception.common.ExceptionType;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ExamRecordExceptionType implements ExceptionType {
	NOT_FOUND_EXAM_RECORD(HttpStatus.NOT_FOUND, "존재하지 않는 시험 응시 기록입니다."),
	CREATE_EXAM_RECORD_FAIL(HttpStatus.BAD_REQUEST, "대학 시험 기록 생성에 실패했습니다."),

	ALREADY_CREATE_EXAM_RECORD(HttpStatus.BAD_REQUEST, "이미 응시한 대학 시험입니다."),
	INVALID_CREATE_REVISION_EXAM_RECORD(HttpStatus.BAD_REQUEST, "첨삭을 받지 않고 재첨삭을 요청할 수 없습니다.");

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
