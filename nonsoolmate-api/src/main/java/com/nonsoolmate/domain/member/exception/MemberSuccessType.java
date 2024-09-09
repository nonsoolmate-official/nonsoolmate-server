package com.nonsoolmate.domain.member.exception;

import org.springframework.http.HttpStatus;

import com.nonsoolmate.global.error.exception.SuccessType;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MemberSuccessType implements SuccessType {
	GET_MEMBER_NAME_SUCCESS(HttpStatus.OK, "이름 조회에 성공하였습니다."),
	GET_MEMBER_TICKET_SUCCESS(HttpStatus.OK, "첨삭권 개수 조회에 성공하였습니다."),
	GET_MEMBER_PROFILE_SUCCESS(HttpStatus.OK, "프로필 조회에 성공하였습니다.");

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
