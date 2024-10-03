package com.nonsoolmate.targetUniversity.controller.dto;

import com.nonsoolmate.university.entity.University;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "TargetUniversityResponseDto", description = "목표 대학 조회 응답 DTO")
public record TargetUniversityResponseDto(
		@Schema(description = "대학 id", example = "1") Long universityId,
		@Schema(description = "대학 이름", example = "중앙대학교") String universityName,
		@Schema(description = "대학 이미지 url", example = "[url 형식]") String universityImageUrl) {

	public static TargetUniversityResponseDto of(University university) {
		return new TargetUniversityResponseDto(
				university.getUniversityId(),
				university.getUniversityName(),
				university.getUniversityImageUrl());
	}
}
