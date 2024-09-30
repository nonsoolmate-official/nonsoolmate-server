package com.nonsoolmate.couponMember.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nonsoolmate.coupon.entity.Coupon;
import com.nonsoolmate.coupon.repository.CouponRepository;
import com.nonsoolmate.couponMember.repository.CouponMemberRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CouponMemberService {
	private final CouponMemberRepository couponMemberRepository;
	private final CouponRepository couponRepository;

	public Coupon validateCoupon(final Long couponMemberId, final String memberId) {
		Long couponId =
				couponMemberRepository.findByCouponMemberIdAndMemberIdThrow(couponMemberId, memberId);
		return couponRepository.findByCouponIdOrThrow(couponId);
	}
}
