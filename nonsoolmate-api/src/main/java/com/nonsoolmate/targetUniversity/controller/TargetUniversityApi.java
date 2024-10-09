package com.nonsoolmate.targetUniversity.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.nonsoolmate.global.security.AuthMember;
import com.nonsoolmate.targetUniversity.controller.dto.request.TargetUniversityRequestDTO;
import com.nonsoolmate.targetUniversity.controller.dto.response.TargetUniversityResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "TargetUniversity", description = "목표 대학과 관련된 API")
public interface TargetUniversityApi {

	@ApiResponses(
			value = {
				@ApiResponse(responseCode = "200", description = "설정 가능한 목표 대학 목록 조회에 성공했습니다."),
			})
	@Operation(summary = "(설정 가능한) 목표 대학 목록 조회", description = "설정 가능한 목표 대학 목록 조회 합니다.")
	ResponseEntity<List<TargetUniversityResponseDTO>> getTargetUniversities(
			@Parameter(hidden = true) @AuthMember String memberId);

	@ApiResponses(
			value = {
				@ApiResponse(responseCode = "200", description = "목표 대학 설정에 성공하였습니다."),
			})
	@Operation(summary = "목표 대학 설정: 리스트 선택", description = "내 목표 대학들 리스트를 설정합니다.")
	ResponseEntity<Void> patchTargetUniversities(
			@Parameter(hidden = true) String memberId, List<TargetUniversityRequestDTO> request);
}
