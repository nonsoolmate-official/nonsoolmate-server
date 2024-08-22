package com.nonsoolmate.nonsoolmateServer.domain.coupon.repository;

import static com.nonsoolmate.nonsoolmateServer.domain.coupon.exception.CouponExceptionType.*;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nonsoolmate.nonsoolmateServer.domain.coupon.entity.Coupon;
import com.nonsoolmate.nonsoolmateServer.domain.coupon.exception.CouponException;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
	Optional<Coupon> findByCouponNumber(String couponNumber);

	default Coupon findByCouponNumberOrThrow(String couponNumber){
		return findByCouponNumber(couponNumber)
			.orElseThrow(() -> new CouponException(NOT_FOUND_COUPON));
	}
}
