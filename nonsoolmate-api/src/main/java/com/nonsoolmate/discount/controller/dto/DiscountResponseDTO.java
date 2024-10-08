package com.nonsoolmate.discount.controller.dto;

import jakarta.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "DiscountResponseDTO", description = "할인 정보 조회 응답 DTO")
public record DiscountResponseDTO(
		@Schema(description = "할인 id", example = "1") long discountId,
		@NotNull @Schema(description = "할인 이름", example = "얼리버드 특가") String discountName,
		@Schema(description = "할인율", example = "0.2") double discountRate) {
	public static DiscountResponseDTO of(
			final Long discountId, final String discountName, final double discountRate) {
		return new DiscountResponseDTO(discountId, discountName, discountRate);
	}
}
