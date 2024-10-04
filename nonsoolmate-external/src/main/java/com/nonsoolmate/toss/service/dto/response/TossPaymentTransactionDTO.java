package com.nonsoolmate.toss.service.dto.response;

public record TossPaymentTransactionDTO(
		String lastTransactionKey, String paymentKey, String approvedAt, Receipt receipt) {
	public record Receipt(String url) {}
}
