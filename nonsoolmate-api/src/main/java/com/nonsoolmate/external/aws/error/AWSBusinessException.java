package com.nonsoolmate.external.aws.error;

import com.nonsoolmate.global.error.exception.BusinessException;

public class AWSBusinessException extends BusinessException {
	public AWSBusinessException(
		AWSExceptionType exceptionType) {
		super(exceptionType);
	}
}
