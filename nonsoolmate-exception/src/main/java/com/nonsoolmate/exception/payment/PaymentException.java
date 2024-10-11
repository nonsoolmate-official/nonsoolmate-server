package com.nonsoolmate.exception.payment;

import com.nonsoolmate.exception.common.ClientException;
import com.nonsoolmate.exception.common.ExceptionType;

public class PaymentException extends ClientException {
  public PaymentException(ExceptionType exceptionType) {
    super(exceptionType);
  }
}
