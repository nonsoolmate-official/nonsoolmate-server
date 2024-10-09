package com.nonsoolmate.exception.payment;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;

import com.nonsoolmate.exception.common.ExceptionType;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum BillingExceptionType implements ExceptionType {
  NOT_FOUND_BILLING(HttpStatus.NOT_FOUND, "사용자가 카드를 등록하지 않았습니다"),
  TOSS_PAYMENT_ISSUE_BILLING(HttpStatus.BAD_REQUEST, "카드 등록에 실패했습니다");

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
