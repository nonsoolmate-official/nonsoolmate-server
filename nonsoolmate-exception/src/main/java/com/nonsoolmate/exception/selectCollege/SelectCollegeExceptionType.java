package com.nonsoolmate.exception.selectCollege;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;

import com.nonsoolmate.exception.common.ExceptionType;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum SelectCollegeExceptionType implements ExceptionType {
  INVALID_SELECTED_COLLEGE(HttpStatus.BAD_REQUEST, "유효한 목표 단과 대학이 아닙니다");

  private final HttpStatus status;
  private final String message;

  @Override
  public HttpStatus status() {
    return this.status;
  }

  @Override
  public String message() {
    return this.message;
  }
}
