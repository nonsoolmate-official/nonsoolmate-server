package com.nonsoolmate.payment.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nonsoolmate.member.entity.Member;
import com.nonsoolmate.member.repository.MemberRepository;
import com.nonsoolmate.payment.controller.dto.request.CreateOrUpdateCardRequestDTO;
import com.nonsoolmate.payment.controller.dto.response.CardResponseDTO;
import com.nonsoolmate.payment.entity.Billing;
import com.nonsoolmate.payment.repository.BillingRepository;
import com.nonsoolmate.toss.service.TossPaymentService;
import com.nonsoolmate.toss.service.vo.TossPaymentBillingVO;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BillingService {
	private final MemberRepository memberRepository;
	private final BillingRepository billingRepository;
	private final TossPaymentService tossPaymentService;

	public CardResponseDTO getCard(final String memberId) {
		Billing billing = billingRepository.findByCustomerIdOrThrow(memberId);
		return CardResponseDTO.of(
				billing.getBillingId(), billing.getCardCompany(), billing.getCardNumber());
	}

	@Transactional
	public CardResponseDTO registerCard(
			final CreateOrUpdateCardRequestDTO request, final String memberId) {
		TossPaymentBillingVO vo = tossPaymentService.issueBilling(memberId, request.authKey());
		Member customer = memberRepository.findByMemberIdOrThrow(memberId);
		Billing billing =
				Billing.builder()
						.customer(customer)
						.billingKey(vo.billingKey())
						.cardCompany(vo.cardCompany())
						.cardNumber(vo.cardNumber())
						.build();

		billingRepository.save(billing);

		return CardResponseDTO.of(
				billing.getBillingId(), billing.getCardCompany(), billing.getCardNumber());
	}

	@Transactional
	public CardResponseDTO updateCard(
			final CreateOrUpdateCardRequestDTO request, final String memberId) {
		Billing billing = billingRepository.findByCustomerIdOrThrow(memberId);
		TossPaymentBillingVO vo = tossPaymentService.issueBilling(memberId, request.authKey());
		billing.updateCardInfo(vo.billingKey(), vo.cardNumber(), vo.cardCompany());

		return CardResponseDTO.of(
				billing.getBillingId(), billing.getCardCompany(), billing.getCardNumber());
	}
}
