package com.nonsoolmate.domain.examRecord.exception;

import com.nonsoolmate.global.error.exception.ClientException;
import com.nonsoolmate.global.error.exception.ExceptionType;

public class ExamRecordException extends ClientException {
	public ExamRecordException(
		ExceptionType exceptionType) {
		super(exceptionType);
	}
}
