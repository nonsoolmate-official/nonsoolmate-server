package com.nonsoolmate.selectCollege.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.nonsoolmate.global.security.AuthMember;
import com.nonsoolmate.response.ErrorResponse;
import com.nonsoolmate.response.SuccessResponse;
import com.nonsoolmate.selectCollege.controller.dto.request.SelectCollegeRequestDTO;
import com.nonsoolmate.selectCollege.controller.dto.response.SelectCollegeExamsResponseDTO;
import com.nonsoolmate.selectCollege.controller.dto.response.SelectCollegeResponseDTO;
import com.nonsoolmate.selectCollege.controller.dto.response.SelectCollegeUpdateResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "SelectCollege", description = "목표 단과 대학과 관련된 API")
public interface SelectCollegeApi {

	@ApiResponses(
			value = {
				@ApiResponse(responseCode = "200", description = "목표 단과 대학 조회에 성공하였습니다."),
			})
	@Operation(summary = "목표 단과 대학 설정: 리스트 조회", description = "내 목표 단과 대학 리스트를 조회합니다.")
	ResponseEntity<SuccessResponse<List<SelectCollegeResponseDTO>>> getSelectColleges(
			@Parameter(hidden = true) @AuthMember String memberId);

	@ApiResponses(
			value = {
				@ApiResponse(responseCode = "200", description = "목표 단과 대학 시험 리스트 조회에 성공하였습니다."),
			})
	@Operation(summary = "마이 페이지: 단과 대학별 시험 리스트 조회", description = "내 목표 단과 대학들의 시험 리스트를 조회합니다.")
	ResponseEntity<SuccessResponse<List<SelectCollegeExamsResponseDTO>>> getSelectCollegeExams(
			@Parameter(hidden = true) @AuthMember String memberId);

	@ApiResponses(
			value = {
				@ApiResponse(responseCode = "200", description = "목표 단과 대학 수정에 성공하였습니다."),
				@ApiResponse(
						responseCode = "400",
						description = "유효한 목표 대학교가 아닙니다",
						content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
			})
	@Operation(summary = "목표 단과 대학 설정: 리스트 선택", description = "내 목표 대학들 리스트를 업데이트(수정) 합니다.")
	ResponseEntity<SuccessResponse<SelectCollegeUpdateResponseDTO>> patchSelectColleges(
			@Parameter(hidden = true) @AuthMember String memberId,
			@RequestBody @Valid List<SelectCollegeRequestDTO> request);
}
