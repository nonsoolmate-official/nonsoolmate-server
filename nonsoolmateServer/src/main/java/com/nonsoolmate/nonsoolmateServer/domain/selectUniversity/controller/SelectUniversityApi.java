package com.nonsoolmate.nonsoolmateServer.domain.selectUniversity.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.nonsoolmate.nonsoolmateServer.domain.member.entity.Member;
import com.nonsoolmate.nonsoolmateServer.domain.selectUniversity.controller.dto.request.SelectUniversityRequestDTO;
import com.nonsoolmate.nonsoolmateServer.domain.selectUniversity.controller.dto.response.SelectCollegeExamsResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.selectUniversity.controller.dto.response.SelectCollegeResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.selectUniversity.controller.dto.response.SelectCollegeUpdateResponseDTO;
import com.nonsoolmate.nonsoolmateServer.global.response.ErrorResponse;
import com.nonsoolmate.nonsoolmateServer.global.response.SuccessResponse;
import com.nonsoolmate.nonsoolmateServer.global.security.AuthUser;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "SelectUniversity", description = "목표 대학과 관련된 API")
public interface SelectUniversityApi {

	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "목표 대학 조회에 성공하였습니다."),})
	@Operation(summary = "목표대학 설정: 리스트 조회", description = "내 목표 단과 대학 리스트를 조회합니다.")
	ResponseEntity<SuccessResponse<List<SelectCollegeResponseDTO>>> getSelectColleges(@AuthUser Member member);

	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "목표 대학 시험 리스트 조회에 성공하였습니다."),})
	@Operation(summary = "마이 페이지: 대학별 시험 리스트 조회", description = "내 목표 대학들의 시험 리스트를 조회합니다.")
	ResponseEntity<SuccessResponse<List<SelectCollegeExamsResponseDTO>>> getSelectCollegeExams(
		@AuthUser Member member);

	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "목표 대학 시험 리스트 조회에 성공하였습니다."),
		@ApiResponse(responseCode = "400", description = "유효한 목표 대학교가 아닙니다", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
	@Operation(summary = "목표대학 설정: 리스트 선택", description = "내 목표 대학들 리스트를 업데이트(수정) 합니다.")
	ResponseEntity<SuccessResponse<SelectCollegeUpdateResponseDTO>> patchSelectUniversities(@AuthUser Member member,
		@RequestBody @Valid List<SelectUniversityRequestDTO> request);
}
