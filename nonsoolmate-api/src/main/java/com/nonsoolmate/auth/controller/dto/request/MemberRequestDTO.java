package com.nonsoolmate.auth.controller.dto.request;

import jakarta.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "MemberRequestDTO", description = "소셜 로그인 요청 DTO")
public record MemberRequestDTO(
    @NotNull @Schema(description = "소셜 플랫폼 타입", example = "NAVER") String platformType) {}
