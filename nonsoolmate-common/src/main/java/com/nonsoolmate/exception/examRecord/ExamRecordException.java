package com.nonsoolmate.exception.examRecord;

import com.nonsoolmate.exception.common.ClientException;
import com.nonsoolmate.exception.common.ExceptionType;

public class ExamRecordException extends ClientException {
	public ExamRecordException(
		ExceptionType exceptionType) {
		super(exceptionType);
	}
}
