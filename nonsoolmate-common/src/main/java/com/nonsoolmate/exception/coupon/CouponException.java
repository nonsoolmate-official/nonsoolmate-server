package com.nonsoolmate.exception.coupon;

import com.nonsoolmate.exception.common.ClientException;
import com.nonsoolmate.exception.common.ExceptionType;

public class CouponException extends ClientException {
	public CouponException(ExceptionType exceptionType) {
		super(exceptionType);
	}
}
