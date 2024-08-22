package com.nonsoolmate.nonsoolmateServer.domain.couponMember.repository;

import java.util.List;

import com.nonsoolmate.nonsoolmateServer.domain.coupon.controller.dto.response.GetCouponResponseDTO;

public interface CouponMemberCustomRepository {
	List<GetCouponResponseDTO> findAllByMemberIdWithCoupon(String memberId);
}
