package com.nonsoolmate.external.discord.exception;

import com.nonsoolmate.global.error.exception.BusinessException;
import com.nonsoolmate.global.error.exception.ExceptionType;

public class ErrorLogAppenderException extends BusinessException {
	public ErrorLogAppenderException(ExceptionType exceptionType) {
		super(exceptionType);
	}
}
