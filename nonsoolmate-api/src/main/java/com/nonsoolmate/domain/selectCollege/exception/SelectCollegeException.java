package com.nonsoolmate.domain.selectCollege.exception;

import com.nonsoolmate.global.error.exception.ClientException;
import com.nonsoolmate.global.error.exception.ExceptionType;

public class SelectCollegeException extends ClientException {
	public SelectCollegeException(ExceptionType exceptionType) {
		super(exceptionType);
	}
}
