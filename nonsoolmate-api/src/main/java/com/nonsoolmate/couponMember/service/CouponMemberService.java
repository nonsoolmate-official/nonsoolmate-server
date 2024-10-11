package com.nonsoolmate.couponMember.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nonsoolmate.couponMember.entity.CouponMember;
import com.nonsoolmate.couponMember.repository.CouponMemberRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CouponMemberService {
  private final CouponMemberRepository couponMemberRepository;

  public CouponMember validateCoupon(final Long couponMemberId, final String memberId) {
    return couponMemberRepository.findByCouponMemberIdAndMemberIdThrow(couponMemberId, memberId);
  }
}
