package com.nonsoolmate.nonsoolmateServer.domain.selectCollege.exception;

import com.nonsoolmate.nonsoolmateServer.global.error.exception.ClientException;
import com.nonsoolmate.nonsoolmateServer.global.error.exception.ExceptionType;

public class SelectCollegeException extends ClientException {
	public SelectCollegeException(ExceptionType exceptionType) {
		super(exceptionType);
	}
}
