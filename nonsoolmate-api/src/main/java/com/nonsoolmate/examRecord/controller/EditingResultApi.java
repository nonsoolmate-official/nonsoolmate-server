package com.nonsoolmate.examRecord.controller;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.nonsoolmate.examRecord.controller.dto.request.UpdateExamRecordResultRequestDTO;
import com.nonsoolmate.examRecord.controller.dto.response.EditingResultDTO;
import com.nonsoolmate.examRecord.entity.enums.EditingType;
import com.nonsoolmate.global.security.AuthMember;
import com.nonsoolmate.response.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "EditingResult", description = "(재)첨삭 결과와 관련된 API")
public interface EditingResultApi {
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200"),
        @ApiResponse(
            responseCode = "404",
            description = "존재하지 않는 시험 응시 기록입니다",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
      })
  @Operation(summary = "(재)첨삭 결과 조회 API", description = "(재)첨삭 결과를 조회합니다.")
  ResponseEntity<EditingResultDTO> getExamRecordResult(
      @Parameter(description = "해당 단과 대학 시험 Id (examId)", required = true) @PathVariable("exam-id")
          final long examId,
      @Parameter(description = "첨삭 유형: 첨삭(EDITING), 재첨삭(REVISION)", required = true)
          @RequestParam("type")
          final EditingType type,
      @Parameter(hidden = true) @AuthMember final String memberId);

  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200"),
        @ApiResponse(
            responseCode = "404",
            description = "존재하지 않는 시험 응시 기록입니다",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
      })
  ResponseEntity<EditingResultDTO> updateExamRecordResult(
      @Parameter(description = "첨삭 결과 등록 request") @Valid
          final UpdateExamRecordResultRequestDTO request);
}
