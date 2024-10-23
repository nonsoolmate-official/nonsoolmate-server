package com.nonsoolmate.member.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "TeacherUniversityDTO", description = "첨삭 전문 대학교 조회 응답 DTO")
public record TeacherUniversityDTO(
    @Schema(description = "대학 이름", example = "논메대학교") String universityName,
    @Schema(description = "대학 사진 url", example = "https://image.png") String universityImageUrl) {

  public static TeacherUniversityDTO of(String universityName, String universityImageUrl) {
    return new TeacherUniversityDTO(universityName, universityImageUrl);
  }
}
