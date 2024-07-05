package com.nonsoolmate.nonsoolmateServer.domain.university.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(name = "UniversityExamAndAnswerResponseDTO", description = "대학시험 문제PDF_해제PDF 조회 응답 DTO")
public record UniversityExamAndAnswerResponseDTO(
        @Schema(description = "시험 이름(대학 + 시험 년도 + 시험 이름)", example = "2023 중앙대학교 경영경제 1") String universityExamName,
        @Schema(description = "시험 문제 이미지 리스트") String universityExamUrl,
        @Schema(description = "이미지 해제 PDF URL") String universityExamAnswerUrl
) {
    public static UniversityExamAndAnswerResponseDTO of(final String universityExamName,
                                                        final String universityExamUrl,
                                                        final String universityExamAnswerUrl) {
        return new UniversityExamAndAnswerResponseDTO(universityExamName, universityExamUrl, universityExamAnswerUrl);
    }
}
