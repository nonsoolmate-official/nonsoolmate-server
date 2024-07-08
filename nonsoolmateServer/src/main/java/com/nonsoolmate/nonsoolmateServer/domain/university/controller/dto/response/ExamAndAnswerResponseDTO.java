package com.nonsoolmate.nonsoolmateServer.domain.university.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ExamAndAnswerResponseDTO", description = "대학시험 문제PDF_해제PDF 조회 응답 DTO")
public record ExamAndAnswerResponseDTO(
	@Schema(description = "시험 이름(대학 + 시험 년도 + 시험 이름)", example = "2023 중앙대학교 경영경제 1") String examName,
	@Schema(description = "시험 문제 PDF URL") String examUrl,
	@Schema(description = "이미지 해제 PDF URL") String examAnswerUrl
) {
	public static ExamAndAnswerResponseDTO of(final String examName,
		final String examUrl,
		final String examAnswerUrl) {
		return new ExamAndAnswerResponseDTO(examName, examUrl, examAnswerUrl);
	}
}
