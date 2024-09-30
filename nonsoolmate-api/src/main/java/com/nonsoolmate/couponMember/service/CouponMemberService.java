package com.nonsoolmate.couponMember.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nonsoolmate.coupon.entity.Coupon;
import com.nonsoolmate.coupon.repository.CouponRepository;
<<<<<<< HEAD
import com.nonsoolmate.couponMember.entity.CouponMember;
=======
>>>>>>> c702253 ([FEAT] CouponMemberService에 validateCoupon 구현)
import com.nonsoolmate.couponMember.repository.CouponMemberRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CouponMemberService {
	private final CouponMemberRepository couponMemberRepository;
	private final CouponRepository couponRepository;

<<<<<<< HEAD
	public CouponMember validateCoupon(final Long couponMemberId, final String memberId) {
		return couponMemberRepository.findByCouponMemberIdAndMemberIdThrow(couponMemberId, memberId);
	}

	public Coupon getCoupon(final Long couponId) {
=======
	public Coupon validateCoupon(final Long couponMemberId, final String memberId) {
		Long couponId =
				couponMemberRepository.findByCouponMemberIdAndMemberIdThrow(couponMemberId, memberId);
>>>>>>> c702253 ([FEAT] CouponMemberService에 validateCoupon 구현)
		return couponRepository.findByCouponIdOrThrow(couponId);
	}
}
