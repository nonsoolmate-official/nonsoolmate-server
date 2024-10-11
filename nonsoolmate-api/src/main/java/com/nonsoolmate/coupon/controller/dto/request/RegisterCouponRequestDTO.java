package com.nonsoolmate.coupon.controller.dto.request;

import jakarta.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "RegisterCouponRequestDTO", description = "쿠폰 등록 요청 DTO")
public record RegisterCouponRequestDTO(
    @NotNull @Schema(description = "쿠폰 번호", example = "1234-5678-abcd") String couponNumber) {}
