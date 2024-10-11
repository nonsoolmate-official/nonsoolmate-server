package com.nonsoolmate.exception.aws;

import com.nonsoolmate.exception.common.BusinessException;

public class AWSBusinessException extends BusinessException {
  public AWSBusinessException(AWSExceptionType exceptionType) {
    super(exceptionType);
  }
}
