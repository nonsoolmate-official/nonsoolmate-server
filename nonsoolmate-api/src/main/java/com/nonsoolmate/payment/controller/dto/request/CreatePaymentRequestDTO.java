package com.nonsoolmate.payment.controller.dto.request;

import jakarta.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "CreatePaymentRequestDTO", description = "상품 결제 요청 DTO")
public record CreatePaymentRequestDTO(
    @Parameter(description = "구매하는 상품 id", required = true) @NotNull Long productId,
    @Parameter(description = "사용자가 적용한 couponMemberId") Long couponMemberId) {}
