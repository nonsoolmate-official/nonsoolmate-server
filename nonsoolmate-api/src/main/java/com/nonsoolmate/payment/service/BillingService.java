package com.nonsoolmate.payment.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import com.nonsoolmate.payment.controller.dto.response.CardResponseDTO;
import com.nonsoolmate.payment.entity.Billing;
import com.nonsoolmate.payment.repository.BillingRepository;

@Service
@RequiredArgsConstructor
public class BillingService {
	private final BillingRepository billingRepository;

	public CardResponseDTO getCard(final String memberId) {
		Billing billing = billingRepository.findByCustomerIdOrThrow(memberId);
		return CardResponseDTO.of(
				billing.getBillingId(), billing.getCardCompany(), billing.getCardNumber());
	}
}
