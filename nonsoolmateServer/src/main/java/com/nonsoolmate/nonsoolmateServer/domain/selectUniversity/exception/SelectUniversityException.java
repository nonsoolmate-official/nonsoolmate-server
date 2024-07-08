package com.nonsoolmate.nonsoolmateServer.domain.selectUniversity.exception;

import com.nonsoolmate.nonsoolmateServer.global.error.exception.ClientException;
import com.nonsoolmate.nonsoolmateServer.global.error.exception.ExceptionType;

public class SelectUniversityException extends ClientException {
	public SelectUniversityException(ExceptionType exceptionType) {
		super(exceptionType);
	}
}
