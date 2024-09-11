package com.nonsoolmate.coupon.entity;

import java.time.LocalDateTime;

import com.nonsoolmate.common.BaseTimeEntity;
import com.nonsoolmate.coupon.entity.enums.CouponType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @note: 쿠폰 종류 : 할인율 쿠폰, 금액 쿠폰, 첨삭권 쿠폰
 *
 * */

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long couponId;

	@NotNull
	private String couponName;

	private String couponDescription;

	private String couponImageUrl;

	@NotNull
	private String couponNumber;

	@NotNull
	@Enumerated(EnumType.STRING)
	private CouponType couponType;

	/**
	 * @note: 쿠폰은 discountRate, discountAmount, ticketCount 중 하나의 깂을 가진다.
	 *
	 * */

	private int discountRate;

	private int discountAmount;

	private int ticketCount;

	private LocalDateTime validStartDate;

	private LocalDateTime validEndDate;

	@Builder
	private Coupon(String couponName, String couponDescription, String couponImageUrl, String couponNumber,
		CouponType couponType, int discountRate, int discountAmount, int ticketCount, LocalDateTime validStartDate,
		LocalDateTime validEndDate) {
		this.couponName = couponName;
		this.couponDescription = couponDescription;
		this.couponImageUrl = couponImageUrl;
		this.couponNumber = couponNumber;
		this.couponType = couponType;
		this.discountRate = discountRate;
		this.discountAmount = discountAmount;
		this.ticketCount = ticketCount;
		this.validStartDate = validStartDate;
		this.validEndDate = validEndDate;
	}
}
