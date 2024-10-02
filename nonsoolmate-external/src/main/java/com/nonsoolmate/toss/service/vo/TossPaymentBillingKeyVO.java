package com.nonsoolmate.toss.service.vo;

public record TossPaymentBillingKeyVO(
		String customerKey, String billingKey, String cardCompany, String cardNumber) {
	public static TossPaymentBillingKeyVO of(
			final String customerKey,
			final String billingKey,
			final String cardCompany,
			final String cardNumber) {
		return new TossPaymentBillingKeyVO(customerKey, billingKey, cardCompany, cardNumber);
	}
}
