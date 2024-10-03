package com.nonsoolmate.targetUniversity.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.nonsoolmate.global.security.AuthMember;
import com.nonsoolmate.targetUniversity.controller.dto.TargetUniversityResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "TargetUniversity", description = "목표 대학과 관련된 API")
public interface TargetUniversityApi {

	@Operation(summary = "(설정 가능한) 목표 대학 목록 조회", description = "설정 가능한 목표 대학 목록 조회 합니다.")
	ResponseEntity<List<TargetUniversityResponseDto>> getTargetUniversities(
			@Parameter(hidden = true) @AuthMember String memberId);
}
