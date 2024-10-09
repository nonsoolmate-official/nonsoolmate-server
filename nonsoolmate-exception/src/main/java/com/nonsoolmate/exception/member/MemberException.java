package com.nonsoolmate.exception.member;

import com.nonsoolmate.exception.common.ClientException;
import com.nonsoolmate.exception.common.ExceptionType;

public class MemberException extends ClientException {
  public MemberException(ExceptionType exceptionType) {
    super(exceptionType);
  }
}
