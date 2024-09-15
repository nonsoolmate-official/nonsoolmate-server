package com.nonsoolmate.payment.entity;

import java.time.LocalDateTime;

import com.nonsoolmate.order.entity.OrderDetail;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
	uniqueConstraints = {
		@UniqueConstraint(name = "UK_TRANSACTION_KEY_CUSTOMER_KEY_KEY", columnNames = {"transactionKey", "order_id"})
	}
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TransactionDetail {
	@Id
	private String transactionKey;

	@NotNull
	private String paymentKey;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	private OrderDetail order;

	@NotNull
	private String receiptUrl;

	@NotNull
	LocalDateTime transactionAt;

	@Builder
	public TransactionDetail(final String transactionKey, final String paymentKey, final OrderDetail order,
		final String receiptUrl, final LocalDateTime transactionAt) {
		this.transactionKey = transactionKey;
		this.paymentKey = paymentKey;
		this.order = order;
		this.receiptUrl = receiptUrl;
		this.transactionAt = transactionAt;
	}
}
