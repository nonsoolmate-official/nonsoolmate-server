package com.nonsoolmate.domain.examRecord.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ExamRecordIdResponse", description = "대학 시험 기록 생성 응답 DTO")
public record ExamRecordIdResponse(
	@Schema(description = "기록된 대학 시험 답안 id", example = "1") Long examRecordId) {
	public static ExamRecordIdResponse of(final Long examRecordId) {
		return new ExamRecordIdResponse(examRecordId);
	}
}