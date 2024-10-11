package com.nonsoolmate.exception.university;

import com.nonsoolmate.exception.common.ClientException;
import com.nonsoolmate.exception.common.ExceptionType;

public class ExamException extends ClientException {
  public ExamException(ExceptionType exceptionType) {
    super(exceptionType);
  }
}
