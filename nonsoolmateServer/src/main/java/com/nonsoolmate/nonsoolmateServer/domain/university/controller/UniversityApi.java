package com.nonsoolmate.nonsoolmateServer.domain.university.controller;

import com.nonsoolmate.nonsoolmateServer.domain.university.controller.dto.response.UniversityExamFileResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.university.controller.dto.response.UniversityExamImageAndAnswerResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.university.controller.dto.response.UniversityExamImageResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.university.controller.dto.response.UniversityExamInfoResponseDTO;
import com.nonsoolmate.nonsoolmateServer.global.response.ErrorResponse;
import com.nonsoolmate.nonsoolmateServer.global.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@Tag(name = "UniversityExam", description = "대학 시험 정보와 관련된 API")
public interface UniversityApi {
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "대학 시험 정보 조회에 성공했습니다"),
                    @ApiResponse(responseCode = "400", description = "존재하지 않는 대학 시험입니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @Operation(summary = "시험 보기: 시험 이름 & 제한 시간", description = "시험 응시 화면의 이름 및 제한 시간을 조회합니다.")
    ResponseEntity<SuccessResponse<UniversityExamInfoResponseDTO>> getUniversityExamInfo(
            @Parameter(description = "해당 대학교 시험 Id (examId)", required = true) @PathVariable("id") Long universityExamId);

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "대학 시험 파일 조회에 성공했습니다"),
                    @ApiResponse(responseCode = "400", description = "존재하지 않는 대학 시험입니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @Operation(summary = "시험 보기: 문제지 [PDF]", description = "시험 응시 화면의 문제지를 조회합니다.")
    ResponseEntity<SuccessResponse<UniversityExamFileResponseDTO>> getUniversityExamFile(
            @Parameter(description = "해당 대학교 시험 Id (examId)", required = true) @PathVariable("id") Long id);

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "대학 시험 이미지 조회에 성공했습니다"),
                    @ApiResponse(responseCode = "400", description = "존재하지 않는 대학 시험입니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @Operation(summary = "시험 보기: 문제지 [페이지네이션]", description = "시험 응시 화면의 문제지를 조회합니다.")
    ResponseEntity<SuccessResponse<Page<UniversityExamImageResponseDTO>>> getUniversityExamImages(
            @Parameter(description = "해당 대학교 시험 Id (examId)", required = true) @PathVariable("id") Long id,
            @Parameter(description = "문제 이미지 페이지 번호 -1") @RequestParam(defaultValue = "0") int page,
            Pageable pageable);

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "대학 시험 이미지 및 해제 PDF 조회에 성공했습니다"),
                    @ApiResponse(responseCode = "400", description = "존재하지 않는 대학 시험입니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @Operation(summary= "해제: 문제이미지_해제PDF", description = "시험 문제 이미지 및 해제 PDF를 조회합니다.")
    ResponseEntity<SuccessResponse<UniversityExamImageAndAnswerResponseDTO>> getUniversityExamImageAndAnswer(
            @PathVariable("id") Long universityExamId);

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "대학 시험 문제 및 해제 PDF 조회에 성공했습니다"),
                    @ApiResponse(responseCode = "400", description = "존재하지 않는 대학 시험입니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @Operation(summary= "해제: 문제PDF_해제PDF", description = "시험 문제 및 해제 PDF를 조회합니다.")
    ResponseEntity<SuccessResponse<UniversityExamImageAndAnswerResponseDTO>> getUniversityExamAndAnswer(
            @PathVariable("id") Long universityExamId);
}
