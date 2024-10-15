package com.nonsoolmate.couponMember.repository;

import static com.nonsoolmate.coupon.entity.QCoupon.*;
import static com.nonsoolmate.couponMember.entity.QCouponMember.*;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;

import com.nonsoolmate.couponMember.repository.dto.CouponResponseDTO;
import com.nonsoolmate.couponMember.repository.dto.QCouponResponseDTO;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
@RequiredArgsConstructor
public class CouponMemberCustomRepositoryImpl implements CouponMemberCustomRepository {
  private final JPAQueryFactory queryFactory;

  @Override
  public List<CouponResponseDTO> findAllByMemberIdWithCoupon(String memberId) {
    return queryFactory
        .select(
            new QCouponResponseDTO(
                couponMember.couponMemberId,
                coupon.couponName,
                coupon.couponDescription,
                coupon.couponImageUrl,
                coupon.couponType,
                coupon.discountRate,
                coupon.discountAmount,
                coupon.ticketCount,
                coupon.validStartDate,
                coupon.validEndDate,
                couponMember.toBeUsed))
        .from(couponMember)
        .where(couponMember.memberId.eq(memberId), couponMember.isUsed.eq(false))
        .leftJoin(coupon)
        .on(couponMember.couponId.eq(coupon.couponId))
        .fetch();
  }
}
