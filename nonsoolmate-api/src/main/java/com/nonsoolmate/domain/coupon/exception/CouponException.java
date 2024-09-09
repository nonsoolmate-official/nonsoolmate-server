package com.nonsoolmate.domain.coupon.exception;

import com.nonsoolmate.global.error.exception.ClientException;
import com.nonsoolmate.global.error.exception.ExceptionType;

public class CouponException extends ClientException {
	public CouponException(ExceptionType exceptionType) {
		super(exceptionType);
	}
}
