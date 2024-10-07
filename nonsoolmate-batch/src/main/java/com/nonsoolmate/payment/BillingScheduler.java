package com.nonsoolmate.payment;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nonsoolmate.coupon.entity.Coupon;
import com.nonsoolmate.coupon.repository.CouponRepository;
import com.nonsoolmate.couponMember.entity.CouponMember;
import com.nonsoolmate.member.entity.Member;
import com.nonsoolmate.member.entity.Membership;
import com.nonsoolmate.member.entity.enums.MembershipType;
import com.nonsoolmate.member.repository.MembershipRepository;
import com.nonsoolmate.order.entity.OrderDetail;
import com.nonsoolmate.order.repository.OrderRepository;
import com.nonsoolmate.order.service.OrderService;
import com.nonsoolmate.payment.service.BillingService;
import com.nonsoolmate.toss.service.TossPaymentService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BillingScheduler {
	private static final Long NO_COUPON_MEMBER_ID = null;

	private final OrderRepository orderRepository;
	private final CouponRepository couponRepository;
	private final MembershipRepository membershipRepository;

	private final BillingService billingService;
	private final OrderService orderService;

	private final TossPaymentService tossPaymentService;

	@Scheduled(cron = "0 0 0 * * *")
	public void regularBillingPayment() {
		List<OrderDetail> orders = orderRepository.findAllByIsPaymentFalse();

		orders.forEach(
				order -> {
					Member member = order.getMember();
					Membership membership = membershipRepository.findByMemberOrThrow(member);
					membership.validateMembershipStatus();

					String billingKey = billingService.getBillingKey(order.getMember().getMemberId());
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
					orderService.createOrder(order.getProduct(), NO_COUPON_MEMBER_ID, memberId);
				});
	}
}
