package com.nonsoolmate.couponMember.entity;

import com.nonsoolmate.common.BaseTimeEntity;

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
public class CouponMember extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long couponMemberId;

	@NotNull
	private String memberId;

	@NotNull
	private Long couponId;

	@NotNull
	private Boolean isUsed;

	@Builder
	private CouponMember(String memberId, Long couponId, Boolean isUsed) {
		this.memberId = memberId;
		this.couponId = couponId;
		this.isUsed = isUsed;
	}
}
