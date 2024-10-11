package com.nonsoolmate.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.nonsoolmate.auth.controller.dto.request.MemberRequestDTO;
import com.nonsoolmate.auth.controller.dto.response.MemberAuthResponseDTO;
import com.nonsoolmate.auth.controller.dto.response.MemberReissueResponseDTO;
import com.nonsoolmate.response.ErrorResponse;
import com.nonsoolmate.response.SuccessResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "auth", description = "인증 관련 API")
public interface AuthApi {

  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "로그인에 성공하였습니다"),
        @ApiResponse(responseCode = "201", description = "회원가입에 성공하였습니다"),
        @ApiResponse(
            responseCode = "400",
            description = "유효하지 않은 플랫폼 인가코드입니다, 입력값이 올바르지 않습니다, 올바르지 않은 플랫폼 유형입니다",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
      })
  @Operation(summary = "소셜 로그인", description = "네이버 소셜 로그인을 합니다.")
  ResponseEntity<SuccessResponse<MemberAuthResponseDTO>> login(
      @Parameter(description = "네이버로부터 발급받은 인가 코드", required = true)
          @RequestHeader(value = "authorization-code")
          final String authorizationCode,
      @RequestBody @Valid final MemberRequestDTO request);

  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "리프레시 토큰 재발급에 성공하였습니다."),
        @ApiResponse(
            responseCode = "400",
            description = "서비스에서 발급되지 않거나 이미 사용된 리프레시 토큰입니다.",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "401",
            description = "기한이 만료된 리프레시 토큰입니다.",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
      })
  @Operation(summary = "액세스 토큰 & 리프레시 토큰 재발급", description = "액세스 토큰 및 리프레시 토큰을 재발급 받습니다.")
  ResponseEntity<SuccessResponse<MemberReissueResponseDTO>> reissue(HttpServletRequest request);
}
