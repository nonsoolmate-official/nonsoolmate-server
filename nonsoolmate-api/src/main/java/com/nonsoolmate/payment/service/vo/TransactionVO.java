package com.nonsoolmate.payment.service.vo;

import java.time.LocalDateTime;

import com.nonsoolmate.order.entity.OrderDetail;

public record TransactionVO(
		String transactionKey,
		String paymentKey,
		String customerKey,
		OrderDetail order,
		String receiptUrl,
		LocalDateTime transactionAt) {
	public static TransactionVO of(
			String transactionKey,
			String paymentKey,
			String customerKey,
			OrderDetail order,
			String receiptUrl,
			LocalDateTime transactionAt) {
		return new TransactionVO(
				transactionKey, paymentKey, customerKey, order, receiptUrl, transactionAt);
	}
}
