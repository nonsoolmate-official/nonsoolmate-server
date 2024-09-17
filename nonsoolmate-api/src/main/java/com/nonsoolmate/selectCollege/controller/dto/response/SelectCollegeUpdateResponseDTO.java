package com.nonsoolmate.selectCollege.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "SelectCollegeUpdateResponseDTO", description = "목표 단과 대학 리스트 선택 응답 DTO")
public record SelectCollegeUpdateResponseDTO(
		@Schema(description = "선택 완료 여부", example = "true") boolean isSelected) {
	public static SelectCollegeUpdateResponseDTO of(final boolean isSelected) {
		return new SelectCollegeUpdateResponseDTO(isSelected);
	}
}
