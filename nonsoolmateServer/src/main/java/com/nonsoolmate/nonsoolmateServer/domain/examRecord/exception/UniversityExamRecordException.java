package com.nonsoolmate.nonsoolmateServer.domain.examRecord.exception;

import com.nonsoolmate.nonsoolmateServer.global.error.exception.ClientException;
import com.nonsoolmate.nonsoolmateServer.global.error.exception.ExceptionType;

public class UniversityExamRecordException extends ClientException {
    public UniversityExamRecordException(
            ExceptionType exceptionType) {
        super(exceptionType);
    }
}
