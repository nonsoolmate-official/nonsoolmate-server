package com.nonsoolmate.nonsoolmateServer.domain.member.exception;

import com.nonsoolmate.nonsoolmateServer.global.error.exception.ClientException;
import com.nonsoolmate.nonsoolmateServer.global.error.exception.ExceptionType;

public class MemberException extends ClientException {
	public MemberException(ExceptionType exceptionType) {
		super(exceptionType);
	}
}
