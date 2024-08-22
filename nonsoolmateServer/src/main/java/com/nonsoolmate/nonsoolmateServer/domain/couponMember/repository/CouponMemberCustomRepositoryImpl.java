package com.nonsoolmate.nonsoolmateServer.domain.couponMember.repository;

import static com.nonsoolmate.nonsoolmateServer.domain.coupon.entity.QCoupon.*;
import static com.nonsoolmate.nonsoolmateServer.domain.couponMember.entity.QCouponMember.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.nonsoolmate.nonsoolmateServer.domain.coupon.controller.dto.response.GetCouponResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.coupon.controller.dto.response.QGetCouponResponseDTO;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CouponMemberCustomRepositoryImpl implements CouponMemberCustomRepository {
	private final JPAQueryFactory queryFactory;

	@Override
	public List<GetCouponResponseDTO> findAllByMemberIdWithCoupon(String memberId) {
		return queryFactory
			.select(new QGetCouponResponseDTO(
				coupon.couponName,
				coupon.couponDescription,
				coupon.couponImageUrl,
				coupon.couponType,
				coupon.discountRate,
				coupon.discountAmount,
				coupon.ticketCount,
				coupon.validStartDate,
				coupon.validEndDate,
				couponMember.isUsed))
			.from(couponMember)
			.where(couponMember.memberId.eq(memberId))
			.leftJoin(coupon).on(couponMember.couponId.eq(coupon.couponId))
			.fetch();
	}
}
