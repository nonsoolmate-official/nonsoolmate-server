package com.nonsoolmate.external.aws.error;

import com.nonsoolmate.global.error.exception.ClientException;

public class AWSClientException extends ClientException {

	public AWSClientException(
		AWSExceptionType exceptionType) {
		super(exceptionType);
	}
}
