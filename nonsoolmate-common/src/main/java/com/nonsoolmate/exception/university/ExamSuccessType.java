package com.nonsoolmate.exception.university;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;

import com.nonsoolmate.exception.common.SuccessType;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ExamSuccessType implements SuccessType {
	/** 200 OK */
	GET_EXAM_SUCCESS(HttpStatus.OK, "대학 시험 정보 조회에 성공했습니다"),
	// TODO: 이미지 조회에서 PDF 조회로 변경되면 지워야 함
	GET_EXAM_IMAGE_SUCCESS(HttpStatus.OK, "대학 시험 이미지 조회에 성공했습니다"),
	GET_EXAM_FILE_SUCCESS(HttpStatus.OK, "대학 시험 파일 조회에 성공했습니다"),
	GET_EXAM_IMAGE_AND_ANSWER_SUCCESS(HttpStatus.OK, "대학 시험 이미지 및 해제 PDF 조회에 성공했습니다"),
	GET_EXAM_AND_ANSWER_SUCCESS(HttpStatus.OK, "대학 시험 문제 및 해제 PDF 조회에 성공했습니다");

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
