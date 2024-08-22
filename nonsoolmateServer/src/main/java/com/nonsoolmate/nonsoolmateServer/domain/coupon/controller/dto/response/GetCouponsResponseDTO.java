package com.nonsoolmate.nonsoolmateServer.domain.coupon.controller.dto.response;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "GetCouponsResponseDTO", description = "쿠폰 목록 조회 DTO")
public record GetCouponsResponseDTO(
	@Schema(description = "쿠폰 조회 DTO", example = "")
	List<GetCouponResponseDTO> coupons
) {
	public static GetCouponsResponseDTO of(List<GetCouponResponseDTO> coupons){
		return new GetCouponsResponseDTO(coupons);
	}
}
