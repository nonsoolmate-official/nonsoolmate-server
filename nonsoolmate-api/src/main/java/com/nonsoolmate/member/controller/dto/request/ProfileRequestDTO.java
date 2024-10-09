package com.nonsoolmate.member.controller.dto.request;

import jakarta.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ProfileRequestDTO", description = "프로필 수정 요청 DTO")
public record ProfileRequestDTO(
    @Schema(description = "멤버 이름", example = "홍길동") @NotNull String name,
    @Schema(description = "성별", example = "M") @NotNull String gender,
    @Schema(description = "태어난 연도", example = "2005") @NotNull String birthYear,
    @Schema(description = "이메일", example = "qwer@gmail.com") @NotNull String email,
    @Schema(description = "전화번호", example = "010-1234-5678") @NotNull String phoneNumber) {}
