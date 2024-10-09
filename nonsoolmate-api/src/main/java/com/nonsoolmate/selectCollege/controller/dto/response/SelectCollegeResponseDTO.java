package com.nonsoolmate.selectCollege.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "SelectCollegeResponseDTO", description = "선택 단과 대학 조회 응답 DTO")
public record SelectCollegeResponseDTO(
    @Schema(description = "단과 대학 id", example = "1") Long collegeId,
    @Schema(description = "대학 이름", example = "중앙대학교") String universityName,
    @Schema(description = "단과대학 이름", example = "경영경제") String collegeName,
    @Schema(description = "대학 선택 여부", example = "true") boolean memberStatus) {
  public static SelectCollegeResponseDTO of(
      final Long collegeId,
      final String universityName,
      final String collegeName,
      final boolean memberStatus) {
    return new SelectCollegeResponseDTO(collegeId, universityName, collegeName, memberStatus);
  }
}
