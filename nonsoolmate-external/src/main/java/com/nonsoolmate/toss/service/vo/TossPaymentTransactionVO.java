package com.nonsoolmate.toss.service.vo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.nonsoolmate.toss.service.dto.response.TossPaymentTransactionDTO;

public record TossPaymentTransactionVO(
		String paymentKey, LocalDateTime transactionAt, String receiptUrl, String transactionKey) {
	public static TossPaymentTransactionVO fromDTO(TossPaymentTransactionDTO dto) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
		LocalDateTime transformedTransactionAt =
				LocalDateTime.parse(dto.approvedAt().substring(0, 19), formatter);
		return new TossPaymentTransactionVO(
				dto.paymentKey(), transformedTransactionAt, dto.receipt().url(), dto.transactionKey());
	}
}
