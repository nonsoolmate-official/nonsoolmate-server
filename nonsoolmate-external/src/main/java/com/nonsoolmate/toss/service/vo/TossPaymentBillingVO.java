package com.nonsoolmate.toss.service.vo;

public record TossPaymentBillingVO(
		String customerKey, String billingKey, String cardCompany, String cardNumber) {
	public static TossPaymentBillingVO of(
			final String customerKey,
			final String billingKey,
			final String cardCompany,
			final String cardNumber) {
		return new TossPaymentBillingVO(customerKey, billingKey, cardCompany, cardNumber);
	}
}
