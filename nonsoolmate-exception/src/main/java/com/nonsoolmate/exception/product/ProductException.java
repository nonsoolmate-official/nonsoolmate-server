package com.nonsoolmate.exception.product;

import com.nonsoolmate.exception.common.ClientException;
import com.nonsoolmate.exception.common.ExceptionType;

public class ProductException extends ClientException {
  public ProductException(ExceptionType exceptionType) {
    super(exceptionType);
  }
}
