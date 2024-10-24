package com.nonsoolmate.member.controller.dto.response;

import java.util.List;

import com.nonsoolmate.global.dto.TagDTO;
import com.nonsoolmate.tag.entity.Tag;
import com.nonsoolmate.teacher.entity.Teacher;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "TeacherResponseDTO", description = "첨삭 선생님 조회 응답 DTO")
public record TeacherResponseDTO(
    @Schema(description = "매칭 여부", example = "true") Boolean isMatched,
    @Schema(description = "첨삭 선생님 id", example = "1") Long teacherId,
    @Schema(description = "첨삭 선생님 이름", example = "홍길동") String teacherName,
    @Schema(description = "첨삭 선생님 프로필 사진 url", example = "[url 형식]") String teacherProfileImageUrl,
    @Schema(description = "첨삭 선생님 한줄 소개", example = "안녕하세요 홍길동입니다.") String introduction,
    @Schema(description = "인증 여부", example = "true") Boolean isCertified,
    @Schema(description = "Q&A 링크", example = "https://www.naver.com") String qnaLink,
    @Schema(description = "첨삭 전문 대학교", example = "") List<TeacherUniversityDTO> teacherUniversities,
    @Schema(description = "선생님 관련 태그", example = "") List<TagDTO> tags) {

  public static TeacherResponseDTO of(
      boolean isMatched,
      Teacher teacher,
      String qnaLink,
      List<TeacherUniversityDTO> universityDTOs,
      List<Tag> tags) {

    if (!isMatched) {
      return new TeacherResponseDTO(false, null, null, null, null, null, null, null, null);
    }

    List<TagDTO> tagDTOs = tags.stream().map(TagDTO::of).toList();

    return new TeacherResponseDTO(
        true,
        teacher.getTeacherId(),
        teacher.getTeacherName(),
        teacher.getTeacherProfileImageUrl(),
        teacher.getIntroduction(),
        teacher.isCertified(),
        qnaLink,
        universityDTOs,
        tagDTOs);
  }
}
