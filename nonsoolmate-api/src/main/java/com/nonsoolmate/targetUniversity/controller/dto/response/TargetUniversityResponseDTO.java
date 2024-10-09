package com.nonsoolmate.targetUniversity.controller.dto.response;

import com.nonsoolmate.university.entity.University;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "TargetUniversityResponseDTO", description = "목표 대학 조회 응답 DTO")
public record TargetUniversityResponseDTO(
    @Schema(description = "대학 id", example = "1") Long universityId,
    @Schema(description = "대학 이름", example = "중앙대학교") String universityName,
    @Schema(description = "대학 이미지 url", example = "[url 형식]") String universityImageUrl) {

  public static TargetUniversityResponseDTO of(University university) {
    return new TargetUniversityResponseDTO(
        university.getUniversityId(),
        university.getUniversityName(),
        university.getUniversityImageUrl());
  }
}
