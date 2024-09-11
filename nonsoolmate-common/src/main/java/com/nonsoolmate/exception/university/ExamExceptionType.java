package com.nonsoolmate.exception.university;

import org.springframework.http.HttpStatus;

import com.nonsoolmate.exception.common.ExceptionType;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ExamExceptionType implements ExceptionType {

	/**
	 * 404 Not Found
	 */
	INVALID_EXAM(HttpStatus.BAD_REQUEST, "존재하지 않는 대학 시험입니다."),
	NOT_FOUND_EXAM_IMAGE(HttpStatus.NOT_FOUND, "존재하지 않는 대학 시험 이미지 입니다.");

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
