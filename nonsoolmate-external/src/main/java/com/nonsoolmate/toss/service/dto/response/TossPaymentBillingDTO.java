package com.nonsoolmate.toss.service.dto.response;

public record TossPaymentBillingDTO(
		String mId,
		String customerKey,
		String authenticatedAt,
		String method,
		String billingKey,
		CardInfo card,
		String cardCompany,
		String cardNumber) {
	public record CardInfo(
			String issuerCode, String acquirerCode, String number, String cardType, String ownerType) {}
}
