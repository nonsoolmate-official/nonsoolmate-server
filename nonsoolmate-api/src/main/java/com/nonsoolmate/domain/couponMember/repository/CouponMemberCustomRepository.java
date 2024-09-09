package com.nonsoolmate.domain.couponMember.repository;

import java.util.List;

import com.nonsoolmate.domain.coupon.controller.dto.response.GetCouponResponseDTO;

public interface CouponMemberCustomRepository {
	List<GetCouponResponseDTO> findAllByMemberIdWithCoupon(String memberId);
}
