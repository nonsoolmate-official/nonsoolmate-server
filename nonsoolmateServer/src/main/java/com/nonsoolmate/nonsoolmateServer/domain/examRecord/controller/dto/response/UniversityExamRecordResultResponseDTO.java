package com.nonsoolmate.nonsoolmateServer.domain.examRecord.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "UniversityExamRecordResultResponseDTO", description = "첨삭 PDF 저장 응답 DTO")
public record UniversityExamRecordResultResponseDTO(
        @Schema(description = "첨삭 결과 PDF URL") String examResultUrl
) {
    public static UniversityExamRecordResultResponseDTO of(final String examResultUrl) {
        return new UniversityExamRecordResultResponseDTO(examResultUrl);
    }
}
