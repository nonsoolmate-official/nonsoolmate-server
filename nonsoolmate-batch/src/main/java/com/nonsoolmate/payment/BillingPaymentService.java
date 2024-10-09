package com.nonsoolmate.payment;

import java.util.Map;

import lombok.RequiredArgsConstructor;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nonsoolmate.coupon.entity.Coupon;
import com.nonsoolmate.coupon.repository.CouponRepository;
import com.nonsoolmate.couponMember.entity.CouponMember;
import com.nonsoolmate.member.entity.Member;
import com.nonsoolmate.member.entity.Membership;
import com.nonsoolmate.member.entity.enums.MembershipType;
import com.nonsoolmate.order.entity.OrderDetail;
import com.nonsoolmate.service.PaymentCommonService;
import com.nonsoolmate.toss.service.TossPaymentService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BillingPaymentService {
  private static final CouponMember NO_COUPON_MEMBER = null;

  private final CouponRepository couponRepository;

  private final TossPaymentService tossPaymentService;
  private final PaymentCommonService paymentCommonService;

  @Async
  @Transactional
  public void processBillingPayment(Map<String, Membership> membershipMap, OrderDetail order) {
    Member member = order.getMember();
    Membership membership = membershipMap.get(member.getMemberId());
    membership.validateMembershipStatus();

    String billingKey = paymentCommonService.getBillingKey(order.getMember().getMemberId());
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
    member.updateTicketCount(
        order.getProduct().getReviewTicketCount(), order.getProduct().getReReviewTicketCount());

    // create next month order
    paymentCommonService.createOrder(order.getProduct(), NO_COUPON_MEMBER, memberId);
  }
}
