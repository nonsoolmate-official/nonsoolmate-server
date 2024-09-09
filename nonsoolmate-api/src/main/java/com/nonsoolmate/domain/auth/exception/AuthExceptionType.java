package com.nonsoolmate.domain.auth.exception;

import org.springframework.http.HttpStatus;

import com.nonsoolmate.global.error.exception.ExceptionType;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum AuthExceptionType implements ExceptionType {

	/**
	 * 400 Bad Request
	 */
	INVALID_MEMBER_PLATFORM_AUTHORIZATION_CODE(HttpStatus.BAD_REQUEST, "유효하지 않은 플랫폼 인가코드입니다."),
	INVALID_PLATFORM_TYPE(HttpStatus.BAD_REQUEST, "올바르지 않은 플랫폼 유형입니다."),
	INVALID_ACCESS_TOKEN(HttpStatus.BAD_REQUEST, "서비스에서 발급되지 않은 액세스 토큰입니다."),
	INVALID_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "서비스에서 발급되지 않거나 이미 사용된 리프레시 토큰입니다."),
	INVALID_REQUEST_LOGIN(HttpStatus.BAD_REQUEST, "올바르지 않은 로그인 요청입니다"),

	/**
	 * 401 Unauthorized
	 */
	UNAUTHORIZED_MEMBER_LOGIN(HttpStatus.UNAUTHORIZED, "로그인에 실패하였습니다."),
	UNAUTHORIZED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "기한이 만료된 액세스 토큰입니다."),
	UNAUTHORIZED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "기한이 만료된 리프레시 토큰입니다.");

	/**
	 * 404 Not Found
	 */

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
