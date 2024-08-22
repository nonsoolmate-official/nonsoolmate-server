package com.nonsoolmate.nonsoolmateServer.domain.coupon.exception;

import org.springframework.http.HttpStatus;

import com.nonsoolmate.nonsoolmateServer.global.error.exception.ExceptionType;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum CouponExceptionType implements ExceptionType {
	INVALID_COUPON_REGISTER(HttpStatus.BAD_REQUEST, "이미 등록된 쿠폰입니다."),
	INVALID_COUPON_ISSUE(HttpStatus.BAD_REQUEST, "올바르지 않는 요청입니다."),
	NOT_FOUND_COUPON(HttpStatus.NOT_FOUND, "유효한 쿠폰을 찾을 수 없습니다.");

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
