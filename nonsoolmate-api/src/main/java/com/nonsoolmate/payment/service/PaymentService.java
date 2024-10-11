package com.nonsoolmate.payment.service;

import static com.nonsoolmate.exception.payment.PaymentExceptionType.*;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nonsoolmate.couponMember.entity.CouponMember;
import com.nonsoolmate.couponMember.service.CouponMemberService;
import com.nonsoolmate.exception.payment.PaymentException;
import com.nonsoolmate.member.entity.Member;
import com.nonsoolmate.member.entity.Membership;
import com.nonsoolmate.member.service.MembershipService;
import com.nonsoolmate.order.entity.OrderDetail;
import com.nonsoolmate.payment.controller.dto.request.CreatePaymentRequestDTO;
import com.nonsoolmate.payment.controller.dto.response.PaymentResponseDTO;
import com.nonsoolmate.payment.entity.TransactionDetail;
import com.nonsoolmate.payment.service.vo.TransactionVO;
import com.nonsoolmate.product.entity.Product;
import com.nonsoolmate.product.entity.enums.ProductType;
import com.nonsoolmate.product.repository.ProductRepository;
import com.nonsoolmate.service.PaymentCommonService;
import com.nonsoolmate.toss.service.TossPaymentService;
import com.nonsoolmate.toss.service.vo.TossPaymentTransactionVO;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentService {
  private final MembershipService membershipService;
  private final TossPaymentService tossPaymentService;
  private final TransactionService transactionService;
  private final PaymentCommonService paymentCommonService;

  private final ProductRepository productRepository;

  private static final CouponMember NO_COUPON_MEMBER = null;
  private final CouponMemberService couponMemberService;

  @Transactional
  public PaymentResponseDTO createBillingPayment(
      final CreatePaymentRequestDTO request, final String memberId) {
    validateMembership(memberId);
    Product product = validateSubscriptionProduct(request.productId());
    Long couponMemberId = request.couponMemberId();

    CouponMember couponMember =
        request.couponMemberId() == null
            ? NO_COUPON_MEMBER
            : couponMemberService.validateCoupon(couponMemberId, memberId);
    OrderDetail order = paymentCommonService.createOrder(product, couponMember, memberId);
    String billingKey = paymentCommonService.getBillingKey(memberId);

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

    if (couponMember != null) {
      couponMember.updateIsUsed(true);
    }

    Membership membership = membershipService.createMembership(memberId, product);
    Member member = membership.getMember();
    member.updateTicketCount(
        order.getProduct().getReviewTicketCount(), order.getProduct().getReReviewTicketCount());

    // create next month order
    paymentCommonService.createOrder(product, NO_COUPON_MEMBER, memberId);

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
