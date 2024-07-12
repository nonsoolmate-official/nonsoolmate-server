package com.nonsoolmate.nonsoolmateServer.external.discord.exception;

import org.springframework.http.HttpStatus;

import com.nonsoolmate.nonsoolmateServer.global.error.exception.ExceptionType;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorLogAppenderExceptionType implements ExceptionType {
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 서버 에러가 발생했습니다"),
	DISCORD_LOG_APPENDER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "디스코드 로그 전송에 실패하였습니다"),
	API_CALL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "외부 API CALL에 실패하였습니다.");

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
