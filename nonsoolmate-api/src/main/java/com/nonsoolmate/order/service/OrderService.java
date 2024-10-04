package com.nonsoolmate.order.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nonsoolmate.coupon.entity.Coupon;
import com.nonsoolmate.couponMember.entity.CouponMember;
import com.nonsoolmate.couponMember.service.CouponMemberService;
import com.nonsoolmate.discountProduct.service.DiscountProductService;
import com.nonsoolmate.member.entity.Member;
import com.nonsoolmate.member.repository.MemberRepository;
import com.nonsoolmate.order.entity.OrderDetail;
import com.nonsoolmate.order.repository.OrderRepository;
import com.nonsoolmate.product.entity.Product;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
	private final OrderRepository orderRepository;
	private final MemberRepository memberRepository;
	private final DiscountProductService discountProductService;
	private final CouponMemberService couponMemberService;

	@Transactional
	public OrderDetail createOrder(
			final Product product, final Long couponMemberId, final String memberId) {
		boolean isCouponApplied = couponMemberId != null;
		if (isCouponApplied) {
			return createOrderWithCoupon(product, couponMemberId, memberId);
		} else {
			return createOrderWithoutCoupon(product, memberId);
		}
	}

	private OrderDetail createOrderWithoutCoupon(final Product product, final String memberId) {
		Member member = memberRepository.findByMemberIdOrThrow(memberId);
		long discountedProductPrice =
				discountProductService.getDiscountedProductPrice(product.getProductId(), memberId);

		OrderDetail order =
				OrderDetail.builder()
						.orderName(product.getProductName())
						.member(member)
						.product(product)
						.amount(discountedProductPrice)
						.build();

		return orderRepository.save(order);
	}

	private OrderDetail createOrderWithCoupon(
			final Product product, final Long couponMemberId, final String memberId) {
		Member member = memberRepository.findByMemberIdOrThrow(memberId);
		CouponMember validCouponMember = couponMemberService.validateCoupon(couponMemberId, memberId);
		long discountedProductPrice =
				discountProductService.getDiscountedProductPrice(product.getProductId(), memberId);
		Coupon validCoupon = couponMemberService.getCoupon(validCouponMember.getCouponId());
		long orderAmount = calculateOrderAmountWithCoupon(discountedProductPrice, validCoupon);

		OrderDetail order =
				OrderDetail.builder()
						.orderName(product.getProductName())
						.member(member)
						.product(product)
						.couponMember(validCouponMember)
						.amount(orderAmount)
						.build();

		return orderRepository.save(order);
	}

	private long calculateOrderAmountWithCoupon(
			final double discountedProductPrice, final Coupon validCoupon) {
		double couponAppliedPrice = discountedProductPrice * (1 - validCoupon.getDiscountRate());
		return Math.round(couponAppliedPrice);
	}
}
