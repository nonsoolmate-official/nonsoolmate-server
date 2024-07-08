package com.nonsoolmate.nonsoolmateServer.global.error.exception;

import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum CommonSuccessType implements SuccessType {
	// 200 OK
	GET_SERVER_PROFILE(HttpStatus.OK, "서버 프로필 확인 완료");

	// 201 created

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