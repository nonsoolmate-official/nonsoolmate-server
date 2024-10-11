package com.nonsoolmate.exception.auth;

import com.nonsoolmate.exception.common.ClientException;
import com.nonsoolmate.exception.common.ExceptionType;

public class AuthException extends ClientException {
  public AuthException(ExceptionType exceptionType) {
    super(exceptionType);
  }
}
