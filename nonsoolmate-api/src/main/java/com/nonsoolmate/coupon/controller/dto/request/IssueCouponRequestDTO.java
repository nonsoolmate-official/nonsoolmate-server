package com.nonsoolmate.coupon.controller.dto.request;

import java.time.LocalDateTime;

import com.nonsoolmate.coupon.entity.Coupon;
import com.nonsoolmate.coupon.entity.enums.CouponType;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class IssueCouponRequestDTO{

	@NotNull
	private final String secretValue;

	@NotNull
	private final String couponName;
	private final String couponDescription;
	private final String couponImageUrl;
	private final String couponNumber;
	@NotNull
	private final CouponType couponType;
	private final int discountRate;
	private final int discountAmount;
	private final int ticketCount;
	private final LocalDateTime validStartDate;
	private final LocalDateTime validEndDate;

	public Coupon toEntity(String requestCouponNumber){
		return Coupon.builder()
			.couponName(couponName)
			.couponDescription(couponDescription)
			.couponImageUrl(couponImageUrl)
			.couponNumber(requestCouponNumber)
			.couponType(couponType)
			.discountRate(discountRate)
			.discountAmount(discountAmount)
			.ticketCount(ticketCount)
			.validStartDate(validStartDate)
			.validEndDate(validEndDate)
			.build();
	}
}
