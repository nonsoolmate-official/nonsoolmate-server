package com.nonsoolmate.member.controller.dto.response;

import java.util.List;

import com.nonsoolmate.global.dto.TagDTO;
import com.nonsoolmate.tag.entity.Tag;
import com.nonsoolmate.teacher.entity.Teacher;
import com.nonsoolmate.teacher.entity.TeacherUniversity;
import com.nonsoolmate.university.entity.University;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "TeacherResponseDTO", description = "첨삭 선생님 조회 응답 DTO")
public record TeacherResponseDTO(
    @Schema(description = "매칭 여부", example = "true") Boolean isMatched,
    @Schema(description = "첨삭 선생님 id", example = "1") Long teacherId,
    @Schema(description = "첨삭 선생님 이름", example = "홍길동") String teacherName,
    @Schema(description = "첨삭 선생님 프로필 사진 url", example = "[url 형식]") String teacherProfileImageUrl,
    @Schema(description = "첨삭 선생님 한줄 소개", example = "안녕하세요 홍길동입니다.") String introduction,
    @Schema(description = "인증 여부", example = "true") Boolean isCertified,
    @Schema(description = "첨삭 전문 대학교", example = "") List<TeacherUniversityDTO> teacherUniversities,
    @Schema(description = "첨삭 전문 대학교", example = "") List<TagDTO> tags) {

  @Schema(name = "TeacherUniversityDTO", description = "첨삭 전문 대학교 조회 응답 DTO")
  public record TeacherUniversityDTO(
      @Schema(description = "대학 이름", example = "1") String universityName,
      @Schema(description = "대학 사진 url", example = "1") String universityImageUrl) {

    public static TeacherUniversityDTO of(TeacherUniversity teacherUniversity) {
      University university = teacherUniversity.getUniversity();
      return new TeacherUniversityDTO(
          university.getUniversityName(), university.getUniversityImageUrl());
    }
  }

  public static TeacherResponseDTO of(
      boolean isMatched, Teacher teacher, List<TeacherUniversity> universities, List<Tag> tags) {

    if (!isMatched) {
      return new TeacherResponseDTO(false, null, null, null, null, null, null, null);
    }

    List<TeacherUniversityDTO> teacherUniversityDTOs =
        universities.stream().map(TeacherUniversityDTO::of).toList();

    List<TagDTO> tagDTOs = tags.stream().map(TagDTO::of).toList();

    return new TeacherResponseDTO(
        true,
        teacher.getTeacherId(),
        teacher.getTeacherName(),
        teacher.getTeacherProfileImageUrl(),
        teacher.getIntroduction(),
        teacher.isCertified(),
        teacherUniversityDTOs,
        tagDTOs);
  }
}
