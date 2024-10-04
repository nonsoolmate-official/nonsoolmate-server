package com.nonsoolmate.couponMember.repository.dto;

import java.time.LocalDateTime;

import lombok.Getter;

import com.nonsoolmate.coupon.entity.enums.CouponType;
import com.querydsl.core.annotations.QueryProjection;

@Getter
public class CouponResponseDTO {

	private final Long couponMemberId;

	private final String couponName;

	private final String couponDescription;

	private final String couponImageUrl;

	private final CouponType couponType;

	private final double discountRate;

	private final int discountAmount;

	private final int ticketCount;

	private final LocalDateTime validStartDate;

	private final LocalDateTime validEndDate;

	private final Boolean isUsed;

	@QueryProjection
	public CouponResponseDTO(
			Long couponMemberId,
			String couponName,
			String couponDescription,
			String couponImageUrl,
			CouponType couponType,
			double discountRate,
			int discountAmount,
			int ticketCount,
			LocalDateTime validStartDate,
			LocalDateTime validEndDate,
			Boolean isUsed) {
		this.couponMemberId = couponMemberId;
		this.couponName = couponName;
		this.couponDescription = couponDescription;
		this.couponImageUrl = couponImageUrl;
		this.couponType = couponType;
		this.discountRate = discountRate;
		this.discountAmount = discountAmount;
		this.ticketCount = ticketCount;
		this.validStartDate = validStartDate;
		this.validEndDate = validEndDate;
		this.isUsed = isUsed;
	}
}
