package com.nonsoolmate.nonsoolmateServer.domain.examRecord.controller.dto.response;

import com.nonsoolmate.nonsoolmateServer.domain.examRecord.entity.enums.EditingType;

public record EditingResultDTO(EditingType editingType, String examResultStatus, String examResultFile) {
	public static EditingResultDTO of(EditingType editingType, String examResultStatus, String examResultFile) {
		return new EditingResultDTO(editingType, examResultStatus, examResultFile);
	}
}
