package com.nonsoolmate.nonsoolmateServer.external.discord.exception;

import com.nonsoolmate.nonsoolmateServer.global.error.exception.BusinessException;
import com.nonsoolmate.nonsoolmateServer.global.error.exception.ExceptionType;

public class ErrorLogAppenderException extends BusinessException {
	public ErrorLogAppenderException(ExceptionType exceptionType) {
		super(exceptionType);
	}
}
