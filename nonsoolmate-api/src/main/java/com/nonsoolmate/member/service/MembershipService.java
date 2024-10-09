package com.nonsoolmate.member.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nonsoolmate.coupon.entity.Coupon;
import com.nonsoolmate.coupon.repository.CouponRepository;
import com.nonsoolmate.discount.controller.dto.DiscountResponseDTO;
import com.nonsoolmate.discountProduct.entity.DiscountProduct;
import com.nonsoolmate.discountProduct.repository.DiscountProductRepository;
import com.nonsoolmate.member.controller.dto.response.GetUsedCouponResponseDTO;
import com.nonsoolmate.member.controller.dto.response.MembershipAndTicketResponseDTO;
import com.nonsoolmate.member.controller.dto.response.PaymentInfoResponseDTO;
import com.nonsoolmate.member.entity.Member;
import com.nonsoolmate.member.entity.Membership;
import com.nonsoolmate.member.entity.enums.MembershipType;
import com.nonsoolmate.member.repository.MemberRepository;
import com.nonsoolmate.member.repository.MembershipRepository;
import com.nonsoolmate.order.entity.OrderDetail;
import com.nonsoolmate.order.repository.OrderRepository;
import com.nonsoolmate.payment.entity.Billing;
import com.nonsoolmate.payment.repository.BillingRepository;
import com.nonsoolmate.product.entity.Product;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MembershipService {
	private final MemberRepository memberRepository;
	private final MembershipRepository membershipRepository;
	private final BillingRepository billingRepository;
	private final OrderRepository orderRepository;
	private final DiscountProductRepository discountProductRepository;
	private final CouponRepository couponRepository;

	public MembershipAndTicketResponseDTO getMembershipAndTicket(final String memberId) {
		Member member = memberRepository.findByMemberIdOrThrow(memberId);
		MembershipType membershipType = getMembershipType(member);
		return MembershipAndTicketResponseDTO.of(
				member.getName(),
				membershipType,
				member.getReviewTicketCount(),
				member.getReReviewTicketCount());
	}

	private MembershipType getMembershipType(final Member member) {
		MembershipType membershipType = membershipRepository.findMembershipTypeOrThrowNull(member);
		boolean existMembership = membershipType != null;
		return existMembership ? membershipType : MembershipType.NONE;
	}

	public boolean checkMembership(final String memberId) {
		Member member = memberRepository.findByMemberIdOrThrow(memberId);
		MembershipType membershipType = getMembershipType(member);
		return membershipType != MembershipType.NONE;
	}

	public Membership createMembership(final String memberId, final Product product) {
		Member member = memberRepository.findByMemberIdOrThrow(memberId);
		MembershipType membershipType = MembershipType.getMembershipType(product.getProductName());
		Membership membership =
				Membership.builder().member(member).membershipType(membershipType).build();
		return membershipRepository.save(membership);
	}

	public Optional<PaymentInfoResponseDTO> getNextPaymentInfo(final String memberId) {
		Optional<Billing> billing = billingRepository.findByCustomerMemberId(memberId);

		if (billing.isEmpty()) {
			return Optional.empty();
		}

		Member member = memberRepository.findByMemberIdOrThrow(memberId);
		Membership membership = membershipRepository.findByMemberOrThrow(member);
		Optional<OrderDetail> nextOrder = orderRepository.findByMemberAndIsPaymentFalse(member);

		if (nextOrder.isEmpty()) {
			return Optional.empty();
		}

		LocalDateTime expectedPaymentDate = membership.getExpectedPaymentDate(nextOrder);
		Product product = nextOrder.get().getProduct();

		Optional<Coupon> usedCoupon = Optional.empty();
		if (nextOrder.get().getCouponMember() != null) {
			Long couponId = nextOrder.get().getCouponMember().getCouponId();
			usedCoupon = couponRepository.findByCouponId(couponId);
		}

		List<DiscountProduct> discountProducts = discountProductRepository.findAllByProduct(product);
		List<DiscountResponseDTO> discountResponseDTOs =
				discountProducts.stream()
						.map(
								p ->
										DiscountResponseDTO.of(
												p.getDiscountProductId(),
												p.getDiscount().getDiscountName(),
												p.getDiscount().getDiscountRate()))
						.toList();

		long totalDiscountPrice = product.getDiscountAmount(discountProducts, usedCoupon);
		long totalPrice = product.getPrice() - totalDiscountPrice;

		return Optional.of(
				PaymentInfoResponseDTO.of(
						expectedPaymentDate,
						billing.get(),
						GetUsedCouponResponseDTO.of(usedCoupon),
						discountResponseDTOs,
						totalDiscountPrice,
						totalPrice));
	}
}
