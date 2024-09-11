package com.nonsoolmate.examRecord.controller.dto.response;


import com.nonsoolmate.examRecord.entity.enums.EditingType;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "EditingResultDTO", description = "(재)첨삭 결과 응답 DTO")
public record EditingResultDTO(
	@Schema(description = "첨삭 유형(EDITING: 첨삭, REVISION: 재첨삭)", example = "EDITING") EditingType editingType,
	@Schema(description = "해당 첨삭 진행 상태(첨삭 진행 중, 첨삭 완료, 재첨삭 진행 중, 재첨삭 완료)", example = "첨삭 진행 중") String examResultStatus,
	@Schema(description = "첨삭 결과 파일 url", example = "https://cloud.nonsoolmate.com/filename") String examResultFileUrl) {
	public static EditingResultDTO of(EditingType editingType, String examResultStatus, String examResultFileUrl) {
		return new EditingResultDTO(editingType, examResultStatus, examResultFileUrl);
	}
}
