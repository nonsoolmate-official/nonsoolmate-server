package com.nonsoolmate.university.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ExamImageResponseDTO", description = "대학 시험 이미지 조회 응답 DTO")
public record ExamImageResponseDTO(@Schema(description = "시험 문제 이미지 URL") String examImgUrl) {
  public static ExamImageResponseDTO of(final String examImgUrl) {
    return new ExamImageResponseDTO(examImgUrl);
  }
}
