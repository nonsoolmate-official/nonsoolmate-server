package com.nonsoolmate.nonsoolmateServer.domain.selectUniversity.controller.dto.response;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "SelectUniversityExamsResponseDTO", description = "대학별 시험 리스트 조회 요청 DTO")
public record SelectUniversityExamsResponseDTO(@Schema(description = "대학 id", example = "대학 이름") Long universityId,
											   @Schema(description = "대학 이름", example = "중앙대학교") String universityName,
											   @Schema(description = "단과대학 이름", example = "경영경제") String universityCollege,
											   @Schema(description = "시험 리스트") List<SelectUniversityExamResponseDTO> examList) {
	public static SelectUniversityExamsResponseDTO of(final Long universityId, final String universityName,
		final String universityCollege,
		final List<SelectUniversityExamResponseDTO> examList) {
		return new SelectUniversityExamsResponseDTO(universityId, universityName, universityCollege, examList);
	}
}
