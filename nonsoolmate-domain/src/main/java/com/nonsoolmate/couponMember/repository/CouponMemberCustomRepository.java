package com.nonsoolmate.couponMember.repository;

import java.util.List;

import com.nonsoolmate.couponMember.repository.dto.CouponResponseDTO;

public interface CouponMemberCustomRepository {
	List<CouponResponseDTO> findAllByMemberIdWithCoupon(String memberId);
}
