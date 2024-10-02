package com.nonsoolmate.toss.service.dto.response;

public record TossPaymentTransactionDTO(
		String paymentKey, String approvedAt, Receipt receipt, String transactionKey) {
	public record Receipt(String url) {}
}
