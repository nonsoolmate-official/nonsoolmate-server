package com.nonsoolmate.exception.selectCollege;

import com.nonsoolmate.exception.common.ClientException;
import com.nonsoolmate.exception.common.ExceptionType;

public class SelectCollegeException extends ClientException {
  public SelectCollegeException(ExceptionType exceptionType) {
    super(exceptionType);
  }
}
