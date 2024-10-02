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
import com.nonsoolmate.payment.controller.dto.request.CreatePaymentRequestDTO;
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
	public OrderDetail createOrder(final CreatePaymentRequestDTO request, final String memberId) {
		Member member = memberRepository.findByMemberIdOrThrow(memberId);
		CouponMember validCouponMember =
				couponMemberService.validateCoupon(request.couponMemberId(), memberId);
		Product product = productRepository.findByProductIdOrThrow(request.productId());
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
