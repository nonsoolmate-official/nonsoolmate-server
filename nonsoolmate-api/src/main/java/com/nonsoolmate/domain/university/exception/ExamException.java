package com.nonsoolmate.domain.university.exception;

import com.nonsoolmate.global.error.exception.ClientException;
import com.nonsoolmate.global.error.exception.ExceptionType;

public class ExamException extends ClientException {
	public ExamException(ExceptionType exceptionType) {
		super(exceptionType);
	}
}
