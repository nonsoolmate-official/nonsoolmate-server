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
import com.nonsoolmate.product.entity.Product;
import com.nonsoolmate.product.entity.enums.ProductType;
import com.nonsoolmate.product.repository.ProductRepository;
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

	private final ProductRepository productRepository;

	private static final Long NO_COUPON_MEMBER_ID = null;

	@Transactional
	public PaymentResponseDTO createBillingPayment(
			final CreatePaymentRequestDTO request, final String memberId) {
		validateMembership(memberId);
		Product product = validateSubscriptionProduct(request.productId());
		Long couponMemberId =
				request.couponMemberId() == null ? NO_COUPON_MEMBER_ID : request.couponMemberId();

		OrderDetail order = orderService.createOrder(product, couponMemberId, memberId);

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
		// TODO: update member review ticket count
		// here

		// create next month order
		orderService.createOrder(product, NO_COUPON_MEMBER_ID, memberId);

		return PaymentResponseDTO.of(transaction.getTransactionKey());
	}

	private Product validateSubscriptionProduct(final Long productId) {
		Product product = productRepository.findByProductIdOrThrow(productId);
		boolean isNotSubscriptionProduct = product.getProductType() != ProductType.SUBSCRIPTION;
		if (isNotSubscriptionProduct) {
			throw new PaymentException(NOT_SUBSCRIPTION_PRODUCT);
		}
		return product;
	}

	private void validateMembership(final String memberId) {
		boolean hasMembership = membershipService.checkMembership(memberId);
		if (hasMembership) {
			throw new PaymentException(ALREADY_MEMBERSHIP_BILLING);
		}
	}
}
