package com.nonsoolmate.exception.common;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum CommonErrorType implements ExceptionType {
	/** 404 Not Found */
	NOT_FOUND_PATH(HttpStatus.NOT_FOUND, "요청하신 경로를 찾을 수 없습니다"),

	/** 500 Internal Server Error */
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다."),
	EXTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "외부 서버에서 오류가 발생하였습니다"),

	// 400 Bad Request
	INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "입력값이 올바르지 않습니다"),
	// TODO:: NAVER API 인증 실패에서도 이 에러 타입 사용하기
	UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "외부 API 인증에 실패하였습니다"),
	// 415 UNSUPPORTED_MEDIA_TYPE
	INVALID_JSON_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "올바른 요청 형식이 아닙니다");

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
