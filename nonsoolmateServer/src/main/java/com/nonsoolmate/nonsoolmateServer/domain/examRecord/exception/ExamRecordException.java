package com.nonsoolmate.nonsoolmateServer.domain.examRecord.exception;

import com.nonsoolmate.nonsoolmateServer.global.error.exception.ClientException;
import com.nonsoolmate.nonsoolmateServer.global.error.exception.ExceptionType;

public class ExamRecordException extends ClientException {
    public ExamRecordException(
            ExceptionType exceptionType) {
        super(exceptionType);
    }
}
