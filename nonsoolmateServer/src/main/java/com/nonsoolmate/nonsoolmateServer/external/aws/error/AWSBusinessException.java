package com.nonsoolmate.nonsoolmateServer.external.aws.error;

import com.nonsoolmate.nonsoolmateServer.global.error.exception.BusinessException;

public class AWSBusinessException extends BusinessException {
	public AWSBusinessException(
		AWSExceptionType exceptionType) {
		super(exceptionType);
	}
}
