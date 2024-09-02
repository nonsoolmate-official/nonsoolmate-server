package com.nonsoolmate.nonsoolmateServer.domain.selectCollege.controller.dto.response;

import com.nonsoolmate.nonsoolmateServer.domain.university.entity.Exam;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "SelectCollegeExamResponseDTO", description = "대학별 시험 조회 요청 DTO")
public record SelectCollegeExamResponseDTO(@Schema(description = "시험 id", example = "1") Long examId,
										   @Schema(description = "시험 이름", example = "경영경제1") String examName,
										   @Schema(description = "시험 제한 시간(초)", example = "3600") int examTimeLimit,
										   @Schema(description = "시험 응시 상태(시험 응시 전, 첨삭 진행 중, 첨삭 완료, 재첨삭 진행 중, 재첨삭 완료 중 하나)", example = "시험 응시 전") String examStatus) {
	public static SelectCollegeExamResponseDTO of(final Exam exam, final String examStatus) {
		return new SelectCollegeExamResponseDTO(exam.getExamId(), exam.getExamListName(), exam.getExamTimeLimit(), examStatus);
	}
}
