package com.nonsoolmate.member.controller.dto.request;

import jakarta.validation.constraints.NotNull;

import com.nonsoolmate.member.entity.enums.MembershipStatus;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "MembershipStatusRequestDTO", description = "멤버십 상태 수정 요청 DTO")
public record MembershipStatusRequestDTO(
    @Schema(description = "멤버십 상태 / 해지하는 경우: TERMINATED, 연장하는 경우: IN_PROGRESS") @NotNull
        MembershipStatus status) {}
