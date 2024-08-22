package com.nonsoolmate.nonsoolmateServer.domain.couponMember.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nonsoolmate.nonsoolmateServer.domain.couponMember.entity.CouponMember;

public interface CouponMemberRepository extends JpaRepository<CouponMember, Long>, CouponMemberCustomRepository {
	Optional<CouponMember> findByCouponId(Long couponId);
}
