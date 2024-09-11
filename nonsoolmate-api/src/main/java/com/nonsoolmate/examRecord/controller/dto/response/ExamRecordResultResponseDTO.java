package com.nonsoolmate.examRecord.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ExamRecordResultResponseDTO", description = "첨삭 PDF 저장 응답 DTO")
public record ExamRecordResultResponseDTO(
	@Schema(description = "첨삭 결과 PDF URL") String examResultUrl
) {
	public static ExamRecordResultResponseDTO of(final String examResultUrl) {
		return new ExamRecordResultResponseDTO(examResultUrl);
	}
}
