package com.nonsoolmate.exception.auth;

import org.springframework.http.HttpStatus;

import com.nonsoolmate.exception.common.SuccessType;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AuthSuccessType implements SuccessType {
	SIGN_UP_SUCCESS(HttpStatus.CREATED, "회원가입에 성공하였습니다."),
	LOGIN_SUCCESS(HttpStatus.OK, "로그인에 성공하였습니다."),
	REISSUE_SUCCESS(HttpStatus.OK, "리프레시 토큰 재발급에 성공하였습니다.");

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
