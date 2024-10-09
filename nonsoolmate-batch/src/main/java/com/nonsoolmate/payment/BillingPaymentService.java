package com.nonsoolmate.payment;

import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nonsoolmate.coupon.entity.Coupon;
import com.nonsoolmate.coupon.repository.CouponRepository;
import com.nonsoolmate.couponMember.entity.CouponMember;
import com.nonsoolmate.discount.entity.Discount;
import com.nonsoolmate.discountProduct.entity.DiscountProduct;
import com.nonsoolmate.discountProduct.repository.DiscountProductRepository;
import com.nonsoolmate.member.entity.Member;
import com.nonsoolmate.member.entity.Membership;
import com.nonsoolmate.member.entity.enums.MembershipType;
import com.nonsoolmate.member.repository.MemberRepository;
import com.nonsoolmate.order.entity.OrderDetail;
import com.nonsoolmate.order.repository.OrderRepository;
import com.nonsoolmate.payment.entity.Billing;
import com.nonsoolmate.payment.repository.BillingRepository;
import com.nonsoolmate.payment.repository.TransactionDetailRepository;
import com.nonsoolmate.product.entity.Product;
import com.nonsoolmate.product.repository.ProductRepository;
import com.nonsoolmate.toss.service.TossPaymentService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BillingPaymentService {
	private static final Long NO_COUPON_MEMBER_ID = null;
	private static final double NOT_FIRST_PURCHASE_DISCOUNT_RATE = 0.0;

	private final BillingRepository billingRepository;
	private final MemberRepository memberRepository;
	private final ProductRepository productRepository;
	private final DiscountProductRepository discountProductRepository;
	private final TransactionDetailRepository transactionRepository;
	private final CouponRepository couponRepository;
	private final OrderRepository orderRepository;

	private final TossPaymentService tossPaymentService;

	@Async
	@Transactional
	public void processBillingPayment(Map<String, Membership> membershipMap, OrderDetail order) {
		Member member = order.getMember();
		Membership membership = membershipMap.get(member.getMemberId());
		membership.validateMembershipStatus();

		String billingKey = getBillingKey(order.getMember().getMemberId());
		String memberId = order.getMember().getMemberId();

		if (order.getCouponMember() != null) {
			CouponMember couponMember = order.getCouponMember();
			Coupon coupon = couponRepository.findByCouponIdOrThrow(couponMember.getCouponId());
			long couponAppliedAmount = coupon.getCouponAppliedAmount(order.getAmount());
			order.updateAmount(couponAppliedAmount);
			couponMember.updateIsUsed(true);
		}

		tossPaymentService.requestBilling(billingKey, memberId, order);

		MembershipType membershipType =
				MembershipType.getMembershipType(order.getProduct().getProductName());
		membership.updateMembershipType(membershipType);

		// create next month order
		createOrder(order.getProduct(), NO_COUPON_MEMBER_ID, memberId);
	}

	private String getBillingKey(final String customerKey) {
		Billing billing = billingRepository.findByCustomerIdOrThrow(customerKey);
		return billing.getBillingKey();
	}

	private void createOrder(
			final Product product, final Long couponMemberId, final String memberId) {
		Member member = memberRepository.findByMemberIdOrThrow(memberId);
		long discountedProductPrice = getDiscountedProductPrice(product.getProductId(), memberId);

		OrderDetail order =
				OrderDetail.builder()
						.orderName(product.getProductName())
						.member(member)
						.product(product)
						.amount(discountedProductPrice)
						.build();

		orderRepository.save(order);
	}

	private long getDiscountedProductPrice(final Long productId, final String memberId) {
		Product product = productRepository.findByProductIdOrThrow(productId);
		double discountedPrice = product.getPrice();

		List<DiscountProduct> discountProducts = discountProductRepository.findAllByProduct(product);
		boolean isFirstPurchaseMember = isFirstPurchase(memberId);

		for (DiscountProduct discountProduct : discountProducts) {
			Discount discount = discountProduct.getDiscount();
			double discountPercent = getDiscountPercent(discount, isFirstPurchaseMember);
			discountedPrice *= (1.0 - discountPercent);
		}

		return Math.round(discountedPrice);
	}

	private boolean isFirstPurchase(final String memberId) {
		return !transactionRepository.existsByCustomerKey(memberId);
	}

	private double getDiscountPercent(final Discount discount, final boolean isFirstPurchaseMember) {
		switch (discount.getDiscountType()) {
			case FIRST_PURCHASE:
				return isFirstPurchaseMember
						? discount.getDiscountRate()
						: NOT_FIRST_PURCHASE_DISCOUNT_RATE;
			default:
				return discount.getDiscountRate();
		}
	}
}
