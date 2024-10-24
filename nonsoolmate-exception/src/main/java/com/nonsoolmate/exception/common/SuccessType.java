package com.nonsoolmate.exception.common;

import org.springframework.http.HttpStatus;

public interface SuccessType {
  HttpStatus status();

  String message();

  default int getHttpStatusCode() {
    return status().value();
  }
}
