package com.nonsoolmate.coupon.repository;

import static com.nonsoolmate.exception.coupon.CouponExceptionType.*;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nonsoolmate.coupon.entity.Coupon;
import com.nonsoolmate.exception.coupon.CouponException;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
  Optional<Coupon> findByCouponNumber(String couponNumber);

  default Coupon findByCouponNumberOrThrow(String couponNumber) {
    return findByCouponNumber(couponNumber)
        .orElseThrow(() -> new CouponException(NOT_FOUND_COUPON));
  }

  Optional<Coupon> findByCouponId(Long couponId);

  default Coupon findByCouponIdOrThrow(Long couponId) {
    return findByCouponId(couponId).orElseThrow(() -> new CouponException(NOT_FOUND_COUPON));
  }
}
