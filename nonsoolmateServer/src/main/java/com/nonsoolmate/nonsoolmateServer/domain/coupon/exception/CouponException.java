package com.nonsoolmate.nonsoolmateServer.domain.coupon.exception;

import com.nonsoolmate.nonsoolmateServer.global.error.exception.ClientException;
import com.nonsoolmate.nonsoolmateServer.global.error.exception.ExceptionType;

public class CouponException extends ClientException {
	public CouponException(ExceptionType exceptionType) {
		super(exceptionType);
	}
}
