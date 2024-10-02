package com.nonsoolmate.couponMember.repository;

import static com.nonsoolmate.exception.coupon.CouponExceptionType.*;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nonsoolmate.couponMember.entity.CouponMember;
import com.nonsoolmate.exception.coupon.CouponException;

public interface CouponMemberRepository
		extends JpaRepository<CouponMember, Long>, CouponMemberCustomRepository {
  
	@Query(
			"SELECT cm FROM CouponMember cm WHERE cm.couponMemberId = :couponMemberId AND cm.memberId = :memberId")
	Optional<CouponMember> findCouponIdByCouponMemberIdAndMemberId(
			final Long couponMemberId, final String memberId);

	default CouponMember findByCouponMemberIdAndMemberIdThrow(
			final Long couponMemberId, final String memberId) {
		return findCouponIdByCouponMemberIdAndMemberId(couponMemberId, memberId)
				.orElseThrow(() -> new CouponException(INVALID_COUPON));
	}

	Optional<CouponMember> findByCouponIdAndMemberId(Long couponId, String memberId);
}
