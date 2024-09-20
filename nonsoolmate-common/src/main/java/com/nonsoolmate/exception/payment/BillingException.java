package com.nonsoolmate.exception.payment;

import com.nonsoolmate.exception.common.ClientException;
import com.nonsoolmate.exception.common.ExceptionType;

public class BillingException extends ClientException {
	public BillingException(ExceptionType exceptionType) {
		super(exceptionType);
	}
}
