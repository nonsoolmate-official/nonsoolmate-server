package com.nonsoolmate.exception.selectCollege;

import org.springframework.http.HttpStatus;

import com.nonsoolmate.exception.common.SuccessType;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SelectCollegeSuccessType implements SuccessType {
	GET_SELECT_COLLEGES_SUCCESS(HttpStatus.OK, "목표 단과 대학 조회에 성공하였습니다."),
	GET_SELECT_COLLEGE_EXAMS_SUCCESS(HttpStatus.OK, "목표 단과 대학 시험 리스트 조회에 성공하였습니다."),
	PATCH_SELECT_COLLEGES_SUCCESS(HttpStatus.OK, "목표 단과 대학 수정에 성공하였습니다.");

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
