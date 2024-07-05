package com.nonsoolmate.nonsoolmateServer.domain.university.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "UniversityExamFileResponseDTO", description = "대학시험 문제 PDF 조회 응답 DTO")
public record UniversityExamFileResponseDTO(
        @Schema(description = "시험 문제 PDF URL") String universityExamUrl
) {
    public static UniversityExamFileResponseDTO of(final String universityExamFileUrl) {
        return new UniversityExamFileResponseDTO(universityExamFileUrl);
    }
}
