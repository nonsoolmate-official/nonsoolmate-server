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
import com.nonsoolmate.product.repository.ProductRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
	private final OrderRepository orderRepository;
	private final MemberRepository memberRepository;
	private final ProductRepository productRepository;
	private final DiscountProductService discountProductService;
	private final CouponMemberService couponMemberService;

	@Transactional
	public OrderDetail createOrder(
			final Long productId, final Long couponMemberId, final String memberId) {
		boolean isCouponApplied = couponMemberId != null;
		if (isCouponApplied) {
			return createOrderWithCoupon(productId, couponMemberId, memberId);
		} else {
			return createOrderWithoutCoupon(productId, memberId);
		}
	}

	private OrderDetail createOrderWithoutCoupon(final Long productId, final String memberId) {
		Member member = memberRepository.findByMemberIdOrThrow(memberId);
		Product product = productRepository.findByProductIdOrThrow(productId);
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
			final Long productId, final Long couponMemberId, final String memberId) {
		Member member = memberRepository.findByMemberIdOrThrow(memberId);
		CouponMember validCouponMember = couponMemberService.validateCoupon(couponMemberId, memberId);
		Product product = productRepository.findByProductIdOrThrow(productId);
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