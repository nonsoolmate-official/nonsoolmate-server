package com.nonsoolmate.exception.member;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;

import com.nonsoolmate.exception.common.ExceptionType;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum MembershipExceptionType implements ExceptionType {
  NOT_FOUND_MEMBERSHIP_TYPE(HttpStatus.NOT_FOUND, "존재하지 않는 멤버십 타입입니다."),
  NOT_FOUND_MEMBERSHIP(HttpStatus.NOT_FOUND, "멤버십이 존재하지 않습니다."),
  TERMINATED_MEMBERSHIP(HttpStatus.BAD_REQUEST, "이미 해지된 멤버십 입니다.");

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
