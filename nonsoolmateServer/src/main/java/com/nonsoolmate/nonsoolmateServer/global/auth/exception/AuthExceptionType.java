package com.nonsoolmate.nonsoolmateServer.global.auth.exception;

import com.nonsoolmate.nonsoolmateServer.global.error.exception.BusinessExceptionType;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum AuthExceptionType implements BusinessExceptionType {

    /**
     * 401 Unauthorized
     */
    UNAUTHORIZED_MEMBER_LOGIN(HttpStatus.UNAUTHORIZED, "로그인에 실패하였습니다.");

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
