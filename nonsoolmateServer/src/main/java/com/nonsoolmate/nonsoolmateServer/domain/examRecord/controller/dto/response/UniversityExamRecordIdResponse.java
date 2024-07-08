package com.nonsoolmate.nonsoolmateServer.domain.examRecord.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "UniversityExamRecordIdResponse", description = "대학 시험 기록 생성 응답 DTO")
public record UniversityExamRecordIdResponse(
        @Schema(description = "기록된 대학 시험 답안 id", example = "1") Long universityExamRecordId) {
    public static UniversityExamRecordIdResponse of(final Long universityExamRecordId) {
        return new UniversityExamRecordIdResponse(universityExamRecordId);
    }
}