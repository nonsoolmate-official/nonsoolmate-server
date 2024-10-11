package com.nonsoolmate.coupon.controller.dto.request;

import jakarta.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ApplyCouponRequestDTO", description = "쿠폰 적용 요청 DTO")
public record ApplyCouponRequestDTO(
    @NotNull @Schema(description = "쿠폰 id", example = "3") Long couponMemberId) {}
