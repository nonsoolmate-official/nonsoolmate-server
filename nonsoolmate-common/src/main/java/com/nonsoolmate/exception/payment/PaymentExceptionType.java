package com.nonsoolmate.exception.payment;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;

import com.nonsoolmate.exception.common.ExceptionType;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum PaymentExceptionType implements ExceptionType {
	ALREADY_MEMBERSHIP_BILLING(HttpStatus.BAD_REQUEST, "이미 멤버십 결제가 진행중입니다");
	;

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
