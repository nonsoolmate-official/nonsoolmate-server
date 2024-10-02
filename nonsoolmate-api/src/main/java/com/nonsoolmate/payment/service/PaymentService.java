package com.nonsoolmate.payment.service;

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
import com.nonsoolmate.payment.entity.TransactionDetail;
import com.nonsoolmate.payment.service.vo.TransactionVO;
import com.nonsoolmate.toss.service.TossPaymentService;
import com.nonsoolmate.toss.service.vo.TossPaymentTransactionVO;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentService {
	private final OrderService orderService;
	private final MembershipService membershipService;
	private final TossPaymentService tossPaymentService;
	private final BillingService billingService;
	private final TransactionService transactionService;

	private static final Long NO_COUPON_MEMBER_ID = null;

	@Transactional
	public PaymentResponseDTO createBillingPayment(
			final CreatePaymentRequestDTO request, final String memberId) {
		validateMembership(memberId);
		OrderDetail order =
				orderService.createOrder(request.productId(), request.couponMemberId(), memberId);
		String billingKey = billingService.getBillingKey(memberId);
		TossPaymentTransactionVO tossPaymentTransactionVO =
				tossPaymentService.requestBilling(billingKey, memberId, order);
		TransactionVO transactionVO =
				TransactionVO.of(
						tossPaymentTransactionVO.transactionKey(),
						tossPaymentTransactionVO.paymentKey(),
						memberId,
						order,
						tossPaymentTransactionVO.receiptUrl(),
						tossPaymentTransactionVO.transactionAt());
		TransactionDetail transaction = transactionService.createTransaction(transactionVO);
		membershipService.createMembership(memberId, order.getProduct());
		// create next month order
		orderService.createOrder(request.productId(), NO_COUPON_MEMBER_ID, memberId);

		return PaymentResponseDTO.of(transaction.getTransactionKey());
	}

	private void validateMembership(final String memberId) {
		boolean hasMembership = membershipService.checkMembership(memberId);
		if (hasMembership) {
			throw new PaymentException(ALREADY_MEMBERSHIP_BILLING);
		}
	}
}
