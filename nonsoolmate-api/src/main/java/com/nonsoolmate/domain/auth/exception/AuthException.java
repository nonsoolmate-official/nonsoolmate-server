package com.nonsoolmate.domain.auth.exception;

import com.nonsoolmate.global.error.exception.ClientException;
import com.nonsoolmate.global.error.exception.ExceptionType;

public class AuthException extends ClientException {
	public AuthException(ExceptionType exceptionType) {
		super(exceptionType);
	}
}
