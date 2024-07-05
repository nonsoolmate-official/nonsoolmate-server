package com.nonsoolmate.nonsoolmateServer.domain.university.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record UniversityExamFileResponseDTO(
        @Schema(description = "시험 문제 PDF URL") String universityExamFileUrl
) {
    public static UniversityExamFileResponseDTO of(final String universityExamFileUrl) {
        return new UniversityExamFileResponseDTO(universityExamFileUrl);
    }
}
