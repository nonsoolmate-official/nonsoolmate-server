package com.nonsoolmate.payment;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nonsoolmate.member.service.MembershipService;
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

	private final BillingService billingService;
	private final MembershipService membershipService;
	private final OrderService orderService;

	private final TossPaymentService tossPaymentService;

	@Scheduled(cron = "0 0 0 * * *")
	public void regularBillingPayment() {
		List<OrderDetail> orders = orderRepository.findAllWithMemberAndProductAndCouponMember();

		orders.forEach(
				order -> {
					String billingKey = billingService.getBillingKey(order.getMember().getMemberId());
					String memberId = order.getMember().getMemberId();

					tossPaymentService.requestBilling(billingKey, memberId, order);

					membershipService.createMembership(memberId, order.getProduct());
					orderService.createOrder(order.getProduct(), NO_COUPON_MEMBER_ID, memberId);
				});
	}
}
