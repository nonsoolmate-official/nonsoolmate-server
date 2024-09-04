package com.nonsoolmate.nonsoolmateServer.domain.examRecord.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.nonsoolmate.nonsoolmateServer.domain.examRecord.controller.dto.request.CreateExamRecordRequestDTO;
import com.nonsoolmate.nonsoolmateServer.domain.examRecord.controller.dto.response.ExamRecordIdResponse;
import com.nonsoolmate.nonsoolmateServer.domain.examRecord.controller.dto.response.ExamRecordResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.examRecord.controller.dto.response.ExamRecordResultResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.examRecord.controller.dto.response.ExamSheetPreSignedUrlResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.member.entity.Member;
import com.nonsoolmate.nonsoolmateServer.global.response.ErrorResponse;
import com.nonsoolmate.nonsoolmateServer.global.response.SuccessResponse;
import com.nonsoolmate.nonsoolmateServer.global.security.AuthMember;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "CollegeExamRecord", description = "시험 응시 기록과 관련된 API")
public interface ExamRecordApi {

	@ApiResponses(
		value = {
			@ApiResponse(responseCode = "200", description = "첨삭 PDF, 해제 PDF 조회에 성공했습니다."),
			@ApiResponse(responseCode = "400", description = "존재하지 않는 대학 시험입니다, 첨삭이 완료되지 않았습니다", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "404", description = "존재하지 않는 시험 응시 기록입니다", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
		}
	)
	@Operation(summary = "첨삭: 첨삭 PDF_해제PDF", description = "첨삭 pdf 및 해제 pdf를 조회합니다.")
	ResponseEntity<SuccessResponse<ExamRecordResponseDTO>> getExamRecord(
		@Parameter(description = "해당 단과 대학 시험 Id (examId)", required = true) @PathVariable("id") Long examId,
		@AuthMember String memberId);

	@ApiResponses(
		value = {
			@ApiResponse(responseCode = "200", description = "첨삭 PDF 조회에 성공했습니다."),
			@ApiResponse(responseCode = "400", description = "존재하지 않는 대학 시험입니다, 첨삭이 완료되지 않았습니다", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "404", description = "존재하지 않는 시험 응시 기록입니다", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
		}
	)
	@Operation(summary = "첨삭: 첨삭 PDF 저장", description = "첨삭 pdf를 조회합니다.")
	ResponseEntity<SuccessResponse<ExamRecordResultResponseDTO>> getExamRecordResult(
		@Parameter(description = "해당 단과 대학 시험 Id (examId)", required = true) @PathVariable("id") Long examId,
		@AuthMember String memberId);

	@ApiResponses(
		value = {
			@ApiResponse(responseCode = "200", description = "답안지 업로드 PresignedUrl 발급에 성공했습니다.")
		}
	)
	@Operation(summary = "시험 보기: [1] 답안지 업로드 PresignedUrl 조회 API", description = "답안지(시험응시기록) 업로드를 위한 PresignedUrl를 조회합니다.")
	ResponseEntity<SuccessResponse<ExamSheetPreSignedUrlResponseDTO>> getExamSheetPreSignedUrl();

	@ApiResponses(
		value = {
			@ApiResponse(responseCode = "201", description = "대학 시험 기록에 성공했습니다."),
			@ApiResponse(responseCode = "400", description = "존재하지 않는 대학 시험입니다, 이미 응시한 대학 시험입니다, 첨삭을 받지 않고 재첨삭을 요청할 수 없습니다", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "404", description = "해당 답안지 파일 이름을 찾을 수 없습니다", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
		}
	)
	@Operation(summary = "시험보기: [3] 답안지 업로드 후 시험 기록 API", description = "답안지(시험응시기록) 업로드 후 서버에 기록하기 위해 호출합니다.")
	ResponseEntity<SuccessResponse<ExamRecordIdResponse>> createExamRecord(
		@Valid @RequestBody CreateExamRecordRequestDTO createExamRecordRequestDTO,
		@AuthMember String memberId);
}
