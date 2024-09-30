package com.nonsoolmate.payment.service;

import static com.nonsoolmate.exception.payment.BillingExceptionType.*;
import static com.nonsoolmate.exception.payment.PaymentExceptionType.*;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nonsoolmate.exception.payment.PaymentException;
import com.nonsoolmate.member.service.MembershipService;
import com.nonsoolmate.order.entity.OrderDetail;
import com.nonsoolmate.order.service.OrderService;
import com.nonsoolmate.payment.controller.dto.request.CreatePaymentRequestDTO;
import com.nonsoolmate.payment.controller.dto.response.PaymentResponseDTO;
import com.nonsoolmate.toss.service.TossPaymentService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentService {
	private final OrderService orderService;
	private final MembershipService membershipService;
	private final TossPaymentService tossPaymentService;

	@Transactional
	public PaymentResponseDTO createMembershipPayment(
			final CreatePaymentRequestDTO request, final String memberId) {

		validateMembership(memberId);

		OrderDetail order = orderService.createOrder(request, memberId);
		// TransactionVO transaction = tossPaymentService.requestPayment(order, memberId);
		// TransactionService.createTransaction(transaction);
		// // TODO Auto-generated method stub
		// return PaymentResponseDTO.of(transaction.getTransacionKey());
		return null;
	}

	private void validateMembership(final String memberId) {
		boolean hasMembership = membershipService.checkMembership(memberId);
		if (hasMembership) {
			throw new PaymentException(ALREADY_MEMBERSHIP_BILLING);
		}
	}
}
