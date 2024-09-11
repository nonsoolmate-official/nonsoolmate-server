package com.nonsoolmate.exception.aws;

import com.nonsoolmate.exception.common.ClientException;

public class AWSClientException extends ClientException {

	public AWSClientException(
		AWSExceptionType exceptionType) {
		super(exceptionType);
	}
}
