package com.nonsoolmate.nonsoolmateServer.domain.auth.exception;

import com.nonsoolmate.nonsoolmateServer.global.error.exception.ClientException;
import com.nonsoolmate.nonsoolmateServer.global.error.exception.ExceptionType;

public class AuthException extends ClientException {
	public AuthException(ExceptionType exceptionType) {
		super(exceptionType);
	}
}
