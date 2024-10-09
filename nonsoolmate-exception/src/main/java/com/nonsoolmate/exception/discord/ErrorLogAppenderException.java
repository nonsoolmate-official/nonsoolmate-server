package com.nonsoolmate.exception.discord;

import com.nonsoolmate.exception.common.BusinessException;
import com.nonsoolmate.exception.common.ExceptionType;

public class ErrorLogAppenderException extends BusinessException {
	public ErrorLogAppenderException(ExceptionType exceptionType) {
		super(exceptionType);
	}
}
