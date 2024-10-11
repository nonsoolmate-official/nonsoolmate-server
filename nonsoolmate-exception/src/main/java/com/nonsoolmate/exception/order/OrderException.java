package com.nonsoolmate.exception.order;

import com.nonsoolmate.exception.common.ClientException;
import com.nonsoolmate.exception.common.ExceptionType;

public class OrderException extends ClientException {
  public OrderException(ExceptionType exceptionType) {
    super(exceptionType);
  }
}
