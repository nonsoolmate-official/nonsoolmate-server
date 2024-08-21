package com.nonsoolmate.nonsoolmateServer.domain.coupon.entity;

import com.nonsoolmate.nonsoolmateServer.domain.common.BaseTimeEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponUser extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long couponUserId;

	@NotNull
	private Long memberId;

	@NotNull
	private Long couponId;

	@NotNull
	private Boolean isUsed;

	@Builder
	private CouponUser(Long memberId, Long couponId, Boolean isUsed) {
		this.memberId = memberId;
		this.couponId = couponId;
		this.isUsed = isUsed;
	}
}
