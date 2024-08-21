package com.nonsoolmate.nonsoolmateServer.domain.selectUniversity.controller.dto.response;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "SelectCollegeExamsResponseDTO", description = "단과 대학별 시험 리스트 조회 요청 DTO")
public record SelectCollegeExamsResponseDTO(@Schema(description = "단과 대학 id", example = "1") Long collegeId,
											@Schema(description = "대학 이름", example = "중앙대학교") String universityName,
											@Schema(description = "단과 대학 이름", example = "경영경제") String CollegeName,
											@Schema(description = "시험 리스트") List<SelectCollegeExamResponseDTO> examList) {
	public static SelectCollegeExamsResponseDTO of(final Long collegeId, final String universityName,
		final String collegeName,
		final List<SelectCollegeExamResponseDTO> examList) {
		return new SelectCollegeExamsResponseDTO(collegeId, universityName, collegeName, examList);
	}
}
