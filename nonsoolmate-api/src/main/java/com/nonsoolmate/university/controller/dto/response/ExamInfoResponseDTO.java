package com.nonsoolmate.university.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ExamInfoResponseDTO", description = "대학 시험 정보 조회 응답 DTO")
public record ExamInfoResponseDTO(
    @Schema(description = "시험 id", example = "1") Long examId,
    @Schema(description = "시험 이름(대학 + 시험 년도 + 시험 이름)", example = "건국대학교 - 2021 인문사회 1")
        String examName,
    @Schema(description = "시험 제한 시간 (초)", example = "6000") int examTimeLimit) {
  public static ExamInfoResponseDTO of(
      final Long examId, final String examName, final int examTimeLimit) {
    return new ExamInfoResponseDTO(examId, examName, examTimeLimit);
  }
}
