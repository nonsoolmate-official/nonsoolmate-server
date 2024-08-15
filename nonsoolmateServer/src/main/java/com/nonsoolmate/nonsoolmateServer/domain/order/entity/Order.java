package com.nonsoolmate.nonsoolmateServer.domain.order.entity;

import com.nonsoolmate.nonsoolmateServer.domain.payment.entity.TransactionDetail;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
	@Id
	private String orderId;

	@NotNull
	private String orderName;

	@OneToOne
	@JoinColumn(name = "transactionKey")
	TransactionDetail transactionDetail;

	@NotNull
	private long amount;

	@Builder
	public Order(final String orderId, final String orderName, final long amount) {
		this.orderId = orderId;
		this.orderName = orderName;
		this.amount = amount;
	}

	public void updateTransactionDetail(final TransactionDetail transactionDetail) {
		this.transactionDetail = transactionDetail;
	}
}
