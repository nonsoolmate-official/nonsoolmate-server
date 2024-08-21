package com.nonsoolmate.nonsoolmateServer.domain.selectCollege.exception;

import org.springframework.http.HttpStatus;

import com.nonsoolmate.nonsoolmateServer.global.error.exception.ExceptionType;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum SelectCollegeExceptionType implements ExceptionType {
	INVALID_SELECTED_UNIVERSITY(HttpStatus.BAD_REQUEST, "유효한 목표 대학교가 아닙니다");

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
