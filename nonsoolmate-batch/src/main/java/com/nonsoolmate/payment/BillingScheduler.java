package com.nonsoolmate.payment;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nonsoolmate.member.entity.Member;
import com.nonsoolmate.member.entity.Membership;
import com.nonsoolmate.member.entity.enums.MembershipStatus;
import com.nonsoolmate.member.repository.MembershipRepository;
import com.nonsoolmate.order.entity.OrderDetail;
import com.nonsoolmate.order.repository.OrderRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BillingScheduler {

	private final OrderRepository orderRepository;
	private final MembershipRepository membershipRepository;

	private final BillingPaymentService billingPaymentService;

	@Scheduled(cron = "0 0 0 * * *")
	@Transactional
	public void regularBillingPayment() {
		List<OrderDetail> orders = orderRepository.findAllByIsPaymentFalse();
		List<Member> members = orders.stream().map(OrderDetail::getMember).toList();
		List<Membership> memberships =
				membershipRepository.findAllByStatusAndMemberIn(MembershipStatus.IN_PROGRESS, members);
		Map<String, Membership> membershipMap =
				memberships.stream()
						.collect(
								Collectors.toMap(
										membership -> membership.getMember().getMemberId(), membership -> membership));

		orders.stream()
				.filter(orderDetail -> membershipMap.containsKey(orderDetail.getMember().getMemberId()))
				.forEach(order -> billingPaymentService.processBillingPayment(membershipMap, order));
	}
}
