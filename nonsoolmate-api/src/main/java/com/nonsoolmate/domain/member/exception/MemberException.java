package com.nonsoolmate.domain.member.exception;

import com.nonsoolmate.global.error.exception.ClientException;
import com.nonsoolmate.global.error.exception.ExceptionType;

public class MemberException extends ClientException {
	public MemberException(ExceptionType exceptionType) {
		super(exceptionType);
	}
}
