package com.nonsoolmate.coupon.controller.dto.response;

import java.util.List;

import com.nonsoolmate.couponMember.repository.dto.CouponResponseDTO;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "GetCouponsResponseDTO", description = "쿠폰 목록 조회 DTO")
public record GetCouponsResponseDTO(
	@Schema(description = "쿠폰 조회 DTO", example = "")
	List<GetCouponResponseDTO> coupons
) {
	public static GetCouponsResponseDTO of(List<CouponResponseDTO> coupons){

		List<GetCouponResponseDTO> responseDTOs = coupons.stream().map(GetCouponResponseDTO::of).toList();

		return new GetCouponsResponseDTO(responseDTOs);
	}
}
