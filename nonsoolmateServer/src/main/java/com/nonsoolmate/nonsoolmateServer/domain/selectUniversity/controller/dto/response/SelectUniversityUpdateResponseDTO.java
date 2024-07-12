package com.nonsoolmate.nonsoolmateServer.domain.selectUniversity.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "SelectUniversityUpdateResponseDTO", description = "목표 대학 리스트 선택 응답 DTO")
public record SelectUniversityUpdateResponseDTO(
	@Schema(description = "선택 완료 여부", example = "true") boolean isSelected
) {
	public static SelectUniversityUpdateResponseDTO of(final boolean isSelected) {
		return new SelectUniversityUpdateResponseDTO(isSelected);
	}
}
