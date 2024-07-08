package com.nonsoolmate.nonsoolmateServer.domain.university.exception;

import com.nonsoolmate.nonsoolmateServer.global.error.exception.ClientException;
import com.nonsoolmate.nonsoolmateServer.global.error.exception.ExceptionType;

public class ExamException extends ClientException {
	public ExamException(ExceptionType exceptionType) {
		super(exceptionType);
	}
}
