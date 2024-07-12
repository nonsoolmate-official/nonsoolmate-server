package com.nonsoolmate.nonsoolmateServer.external.aws.error;

import com.nonsoolmate.nonsoolmateServer.global.error.exception.ClientException;

public class AWSClientException extends ClientException {

	public AWSClientException(
		AWSExceptionType exceptionType) {
		super(exceptionType);
	}
}
