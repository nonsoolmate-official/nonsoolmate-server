package com.nonsoolmate.nonsoolmateServer.domain.university.controller.dto.response;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ExamImageAndAnswerResponseDTO", description = "대학시험 문제이미지_해제PDF 조회 응답 DTO")
public record ExamImageAndAnswerResponseDTO(
	@Schema(description = "시험 이름(대학 + 시험 년도 + 시험 이름)", example = "2023 중앙대학교 경영경제 1") String examName,
	@Schema(description = "시험 문제 이미지 리스트") List<ExamImageResponseDTO> examQuestionList,
	@Schema(description = "이미지 해제 PDF URL") String examAnswerUrl
) {
	public static ExamImageAndAnswerResponseDTO of(final String examName,
		final List<ExamImageResponseDTO> examQuestionList,
		final String examAnswerUrl) {
		return new ExamImageAndAnswerResponseDTO(examName, examQuestionList, examAnswerUrl);
	}
}

