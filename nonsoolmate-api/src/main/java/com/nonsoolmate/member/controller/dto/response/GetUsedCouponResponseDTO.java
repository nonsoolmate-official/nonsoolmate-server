package com.nonsoolmate.member.controller.dto.response;

import java.util.Optional;

import com.nonsoolmate.coupon.entity.Coupon;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "GetUsedCouponResponseDTO", description = "다음달 결제에 쓰이는 쿠폰 DTO")
public record GetUsedCouponResponseDTO(
		@Schema(description = "사용자가 등록한 coupon의 id", example = "1") Long couponId,
		@Schema(description = "쿠폰 이름", example = "쿠폰 이름입니다.") String couponName,
		@Schema(description = "쿠폰 이미지 url", example = "[url] 형식") String couponImageUrl,
		@Schema(description = "RATE 일 경우 할인율", example = "0.2") double discountRate) {
	public static GetUsedCouponResponseDTO of(Optional<Coupon> coupon) {
		return coupon
				.map(
						value ->
								new GetUsedCouponResponseDTO(
										value.getCouponId(),
										value.getCouponName(),
										value.getCouponImageUrl(),
										value.getDiscountRate()))
				.orElseGet(() -> new GetUsedCouponResponseDTO(null, null, null, 0));
	}
}
