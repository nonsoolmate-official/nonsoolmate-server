package com.nonsoolmate.nonsoolmateServer.domain.selectUniversity.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "SelectUniversityExamResponseDTO", description = "대학별 시험 조회 요청 DTO")
public record SelectUniversityExamResponseDTO(@Schema(description = "시험 id", example = "1") Long examId,
											  @Schema(description = "시험 이름", example = "경영경제1") String examName,
											  @Schema(description = "시험 제한 시간(초)", example = "3600") int examTimeLimit,
											  @Schema(description = "시험 응시 상태", example = "시험 응시 전") String examStatus) {
	public static SelectUniversityExamResponseDTO of(final Long examId, final String examName, final int examTimeLimit,
		final String examStatus) {
		return new SelectUniversityExamResponseDTO(examId, examName, examTimeLimit, examStatus);
	}
}
