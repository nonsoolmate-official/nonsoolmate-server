package com.nonsoolmate.university.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import com.nonsoolmate.university.controller.dto.response.ExamAndAnswerResponseDTO;
import com.nonsoolmate.university.controller.dto.response.ExamInfoResponseDTO;
import com.nonsoolmate.university.controller.dto.response.ExamUrlResponseDTO;
import com.nonsoolmate.response.ErrorResponse;
import com.nonsoolmate.response.SuccessResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "CollegeExam", description = "단과 대학 시험 정보와 관련된 API")
public interface ExamApi {
	@ApiResponses(
		value = {
			@ApiResponse(responseCode = "200", description = "대학 시험 정보 조회에 성공했습니다"),
			@ApiResponse(responseCode = "400", description = "존재하지 않는 대학 시험입니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
		}
	)
	@Operation(summary = "시험 보기: 시험 이름 & 제한 시간", description = "시험 응시 화면의 이름 및 제한 시간을 조회합니다.")
	ResponseEntity<SuccessResponse<ExamInfoResponseDTO>> getExamInfo(
		@Parameter(description = "해당 단과 대학교 시험 Id (examId)", required = true) @PathVariable("id") Long examId);

	@ApiResponses(
		value = {
			@ApiResponse(responseCode = "200", description = "대학 시험 파일 조회에 성공했습니다"),
			@ApiResponse(responseCode = "400", description = "존재하지 않는 대학 시험입니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
		}
	)
	@Operation(summary = "시험 보기: 문제지 [PDF]", description = "시험 응시 화면의 문제지를 조회합니다.")
	ResponseEntity<SuccessResponse<ExamUrlResponseDTO>> getExamFile(
		@Parameter(description = "해당 단과 대학교 시험 Id (examId)", required = true) @PathVariable("id") Long id);

	@ApiResponses(
		value = {
			@ApiResponse(responseCode = "201", description = "대학 시험 문제 및 해제 PDF 조회에 성공했습니다"),
			@ApiResponse(responseCode = "400", description = "존재하지 않는 대학 시험입니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
		}
	)
	@Operation(summary = "해제: 문제PDF_해제PDF", description = "시험 문제 및 해제 PDF를 조회합니다.")
	ResponseEntity<SuccessResponse<ExamAndAnswerResponseDTO>> getExamAndAnswer(
		@PathVariable("id") Long examId);
}
