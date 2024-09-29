package com.nonsoolmate.payment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.nonsoolmate.global.security.AuthMember;
import com.nonsoolmate.payment.controller.dto.request.CreateOrUpdateCardRequestDTO;
import com.nonsoolmate.payment.controller.dto.response.CardResponseDTO;
import com.nonsoolmate.response.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "card", description = "카드 등록과 관련된 API")
public interface CardApi {
	@ApiResponses(
			value = {
				@ApiResponse(responseCode = "200", description = "사용자 카드 정보 조회에 성공했습니다"),
				@ApiResponse(
						responseCode = "404",
						description = "사용자가 카드를 등록하지 않았습니다",
						content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
			})
	@Operation(summary = "카드 등록 페이지: 사용자 카드 정보 조회", description = "사용자가 이전에 등록한 카드 정보를 조회합니다")
	ResponseEntity<CardResponseDTO> getCard(@Parameter(hidden = true) @AuthMember String memberId);

	@ApiResponses(
			value = {
				@ApiResponse(responseCode = "201", description = "카드 등록에 성공했습니다"),
				@ApiResponse(
						responseCode = "400",
						description = "카드 등록에 실패했습니다 (customerKey, authKey가 잘못된 경우)",
						content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
			})
	@Operation(summary = "카드 등록 페이지: 사용자 카드 등록", description = "사용자가 이전에 등록한 카드 정보를 조회합니다")
	ResponseEntity<CardResponseDTO> registerCard(
			@RequestBody CreateOrUpdateCardRequestDTO createOrUpdateCardRequestDTO,
			@Parameter(hidden = true) @AuthMember String memberId);

	@ApiResponses(
			value = {
				@ApiResponse(responseCode = "200", description = "카드 변경에 성공했습니다"),
				@ApiResponse(
						responseCode = "400",
						description = "카드 등록에 실패했습니다 (customerKey, authKey가 잘못된 경우)",
						content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
				@ApiResponse(
						responseCode = "404",
						description = "사용자가 카드를 등록하지 않았습니다",
						content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
			})
	@Operation(summary = "카드 등록 페이지: 사용자 카드 변경", description = "사용자가 이전에 등록한 카드 정보를 조회합니다")
	ResponseEntity<CardResponseDTO> updateCard(
			@RequestBody CreateOrUpdateCardRequestDTO createOrUpdateCardRequestDTO,
			@Parameter(hidden = true) @AuthMember String memberId);
}
