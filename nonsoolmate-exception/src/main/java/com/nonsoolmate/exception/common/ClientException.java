package com.nonsoolmate.exception.common;

import lombok.Getter;

@Getter
public class ClientException extends RuntimeException {
  private final ExceptionType exceptionType;

  public ClientException(ExceptionType exceptionType) {
    super(exceptionType.message());
    this.exceptionType = exceptionType;
  }
}
